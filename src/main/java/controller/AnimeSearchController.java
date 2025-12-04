package controller;

import dto.AnimeDto;
import dto.SearchResponseDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import service.AnimeSearchService;


@Controller
public class AnimeSearchController {
  
  @Autowired
  private AnimeSearchService searchService;
  
  /**
   * Home page - displays the search interface.
   */
  @GetMapping("/")
  public String home(Model model) {
    searchService.initialize();
    
    Map<String, Integer> history = searchService.getSearchHistory();
    model.addAttribute("searchHistory", history);
    
    return "index";
  }
  
  /**
   * Fetch fresh data from API.
   */
  @PostMapping("/api/fetch")
  @ResponseBody
  public Map<String, String> fetchApiData() {
    String data = searchService.fetchAndCacheApiData();
    
    if (!data.isEmpty()) {
      return Map.of(
          "status", "success",
          "message", "Successfully fetched anime data from API",
          "dataLength", String.valueOf(data.length())
      );
    } else {
      return Map.of(
          "status", "error",
          "message", "Failed to fetch data from API"
      );
    }
  }
  
  /**
   * Search for words and return results with performance comparison.
   */
  @PostMapping("/api/search")
  @ResponseBody
  public SearchResponseDto search(@RequestParam("words") String words) {
    List<String> searchWords = new ArrayList<>();
    if (words != null && !words.trim().isEmpty()) {
      String[] wordArray = words.split(",");
      for (String word : wordArray) {
        String trimmed = word.trim();
        if (!trimmed.isEmpty()) {
          searchWords.add(trimmed);
        }
      }
    }
    
    return searchService.searchWords(searchWords);
  }
  
  /**
   * Get search history.
   */
  @GetMapping("/api/history")
  @ResponseBody
  public Map<String, Integer> getHistory() {
    return searchService.getSearchHistory();
  }
  
  /**
   * View raw API data.
   */
  @GetMapping("/data")
  public String viewData(Model model) {
    String apiData = searchService.getApiData();
    model.addAttribute("apiData", apiData);
    model.addAttribute("dataLength", apiData.length());
    
    return "data";
  }
  
  /**
   * Clear cache and force refresh.
   */
  @PostMapping("/api/refresh")
  @ResponseBody
  public Map<String, String> refresh() {
    searchService.clearCache();
    return Map.of(
        "status", "success",
        "message", "Cache cleared successfully"
    );
  }
  
  /**
   * Get anime list as JSON.
   */
  @GetMapping("/api/anime")
  @ResponseBody
  public List<AnimeDto> getAnimeList() {
    return searchService.getAnimeList();
  }
}
