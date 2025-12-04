package service;

import api.ApiCall;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dto.AnimeDto;
import dto.PerformanceDto;
import dto.SearchResponseDto;
import dto.SearchResultDto;
import file.FileHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.springframework.stereotype.Service;
import searcher.InvertedIndexSearcher;
import searcher.PerformanceMeasurer;
import searcher.RegExSearcher;
import searcher.SearchHistory;


@Service
public class AnimeSearchService {
  
  private static final String API_DATA_FILE = "api_data.json";
  private static final int DEFAULT_ITERATIONS = 10;
  
  private String cachedApiData = null;
  private InvertedIndexSearcher cachedIndexSearcher = null;
  
  /**
   * Initialize the service by loading search history.
   */
  public void initialize() {
    SearchHistory.loadHistory();
  }
  
  /**
   * Fetch anime data from API and cache it.
   */
  public String fetchAndCacheApiData() {
    String apiData = ApiCall.fetchData();
    
    if (!apiData.isEmpty()) {
      cachedApiData = apiData;
      FileHandler.saveToFile(apiData, API_DATA_FILE);
      cachedIndexSearcher = new InvertedIndexSearcher(apiData);
    }
    
    return apiData;
  }
  
  /**
   * Get cached API data or fetch from file.
   */
  public String getApiData() {
    if (cachedApiData == null || cachedApiData.isEmpty()) {
      cachedApiData = FileHandler.readFromFile(API_DATA_FILE);
      if (!cachedApiData.isEmpty() && cachedIndexSearcher == null) {
        cachedIndexSearcher = new InvertedIndexSearcher(cachedApiData);
      }
    }
    return cachedApiData;
  }
  
  /**
   * Get or build inverted index searcher.
   */
  private InvertedIndexSearcher getIndexSearcher() {
    if (cachedIndexSearcher == null) {
      String data = getApiData();
      if (!data.isEmpty()) {
        cachedIndexSearcher = new InvertedIndexSearcher(data);
      }
    }
    return cachedIndexSearcher;
  }
  
  /**
   * Search for words and compare algorithm performance.
   */
  public SearchResponseDto searchWords(List<String> searchWords) {
    SearchResponseDto response = new SearchResponseDto();
    
    if (searchWords == null || searchWords.isEmpty()) {
      response.setResults(new ArrayList<>());
      response.setPerformanceData(new ArrayList<>());
      return response;
    }
    
    String fileContent = getApiData();
    if (fileContent.isEmpty()) {
      response.setResults(new ArrayList<>());
      response.setPerformanceData(new ArrayList<>());
      return response;
    }
    
    InvertedIndexSearcher indexSearcher = getIndexSearcher();
    
    List<String> lowerCaseWords = new ArrayList<>();
    for (String word : searchWords) {
      lowerCaseWords.add(word.toLowerCase());
    }
    
    Function<List<String>, Map<String, Integer>> regexSearchFunction = 
        words -> RegExSearcher.searchWords(fileContent, words);
    Function<List<String>, Map<String, Integer>> invertedIndexSearchFunction = 
        words -> indexSearcher.searchWords(words);
    
    long regexTime = PerformanceMeasurer.measureSearchTimeWithIterations(
        regexSearchFunction, fileContent, lowerCaseWords, DEFAULT_ITERATIONS);
    long invertedIndexTime = PerformanceMeasurer.measureSearchTimeWithIterations(
        invertedIndexSearchFunction, fileContent, lowerCaseWords, DEFAULT_ITERATIONS);
    
    Map<String, Integer> regexResults = RegExSearcher.searchWords(fileContent, lowerCaseWords);
    Map<String, Integer> invertedIndexResults = indexSearcher.searchWords(lowerCaseWords);
    
    List<SearchResultDto> results = new ArrayList<>();
    List<PerformanceDto> performanceData = new ArrayList<>();
    
    for (String word : lowerCaseWords) {
      SearchHistory.addSearchWord(word);
      
      SearchResultDto resultDto = new SearchResultDto(
          word,
          regexResults.getOrDefault(word, 0),
          invertedIndexResults.getOrDefault(word, 0),
          SearchHistory.getSearchCount(word)
      );
      results.add(resultDto);
      
      List<String> singleWord = List.of(word);
      long regexWordTime = PerformanceMeasurer.measureSearchTimeWithIterations(
          regexSearchFunction, fileContent, singleWord, DEFAULT_ITERATIONS);
      long indexWordTime = PerformanceMeasurer.measureSearchTimeWithIterations(
          invertedIndexSearchFunction, fileContent, singleWord, DEFAULT_ITERATIONS);
      
      PerformanceDto perfDto = new PerformanceDto(
          word,
          regexWordTime,
          indexWordTime,
          PerformanceMeasurer.formatTime(regexWordTime),
          PerformanceMeasurer.formatTime(indexWordTime)
      );
      performanceData.add(perfDto);
    }
    
    SearchHistory.saveHistory();
    
    response.setResults(results);
    response.setPerformanceData(performanceData);
    response.setTotalRegexTime(regexTime);
    response.setTotalInvertedIndexTime(invertedIndexTime);
    response.setTotalRegexTimeFormatted(PerformanceMeasurer.formatTime(regexTime));
    response.setTotalInvertedIndexTimeFormatted(PerformanceMeasurer.formatTime(invertedIndexTime));
    response.setIterations(DEFAULT_ITERATIONS);
    
    return response;
  }
  
  /**
   * Get all search history.
   */
  public Map<String, Integer> getSearchHistory() {
    return SearchHistory.getAllSearchCounts();
  }
  
  /**
   * Clear cached data (useful for forcing a refresh).
   */
  public void clearCache() {
    cachedApiData = null;
    cachedIndexSearcher = null;
  }
  
  /**
   * Parse and return anime list from cached API data.
   */
  public List<AnimeDto> getAnimeList() {
    List<AnimeDto> animeList = new ArrayList<>();
    String apiData = getApiData();
    
    if (apiData == null || apiData.isEmpty()) {
      return animeList;
    }
    
    try {
      JsonObject jsonObject = JsonParser.parseString(apiData).getAsJsonObject();
      JsonArray dataArray = jsonObject.getAsJsonArray("data");
      
      if (dataArray != null) {
        for (JsonElement element : dataArray) {
          JsonObject animeObj = element.getAsJsonObject();
          AnimeDto anime = new AnimeDto();
          
          anime.setMalId(animeObj.get("mal_id").getAsInt());
          anime.setTitle(getStringOrNull(animeObj, "title"));
          anime.setTitleEnglish(getStringOrNull(animeObj, "title_english"));
          
          if (animeObj.has("images") && !animeObj.get("images").isJsonNull()) {
            JsonObject images = animeObj.getAsJsonObject("images");
            if (images.has("jpg") && !images.get("jpg").isJsonNull()) {
              JsonObject jpg = images.getAsJsonObject("jpg");
              anime.setImageUrl(getStringOrNull(jpg, "image_url"));
            }
          }
          
          anime.setType(getStringOrNull(animeObj, "type"));
          
          if (animeObj.has("episodes") && !animeObj.get("episodes").isJsonNull()) {
            anime.setEpisodes(animeObj.get("episodes").getAsInt());
          }
          
          anime.setStatus(getStringOrNull(animeObj, "status"));
          
          if (animeObj.has("score") && !animeObj.get("score").isJsonNull()) {
            anime.setScore(animeObj.get("score").getAsDouble());
          }
          
          anime.setSynopsis(getStringOrNull(animeObj, "synopsis"));
          
          if (animeObj.has("year") && !animeObj.get("year").isJsonNull()) {
            anime.setYear(String.valueOf(animeObj.get("year").getAsInt()));
          }
          
          anime.setRating(getStringOrNull(animeObj, "rating"));
          
          animeList.add(anime);
        }
      }
    } catch (Exception e) {
      System.err.println("Error parsing anime data: " + e.getMessage());
    }
    
    return animeList;
  }
  
  /**
   * Helper method to safely get string from JSON object.
   */
  private String getStringOrNull(JsonObject obj, String key) {
    if (obj.has(key) && !obj.get(key).isJsonNull()) {
      return obj.get(key).getAsString();
    }
    return null;
  }
}
