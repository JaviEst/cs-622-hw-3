import api.ApiCall;
import file.FileHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import searcher.InvertedIndexSearcher;
import searcher.PerformanceMeasurer;
import searcher.RegExSearcher;
import searcher.SearchHistory;

public class Main {
  public static void main(String[] args) {
    try (Scanner scanner = new Scanner(System.in)) {
      // Load search history
      SearchHistory.loadHistory();
      
      // Fetch data from API
      System.out.println("Fetching data from API...");
      String apiData = ApiCall.fetchData();
      
      if (apiData.isEmpty()) {
        System.out.println("Failed to fetch data from API. Exiting...");
        return;
      }
      
      // Save data to file
      String fileName = "api_data.json";
      System.out.printf("Saving data to file %s ...%n", fileName);
      FileHandler.saveToFile(apiData, fileName);
      
      // Read data from file
      System.out.printf("Reading data from file %s ...%n", fileName);
      String fileContent = FileHandler.readFromFile(fileName);
      
      if (fileContent.isEmpty()) {
        System.out.println("Failed to read data from file. Exiting...");
        return;
      }
      
      // Get user input for words to search
      System.out.printf("%nEnter words to search for (press Enter with empty input when done):%n");
      List<String> searchWords = new ArrayList<>();
      
      while (true) {
        System.out.print("Enter word: ");
        String word = scanner.nextLine().trim();
        
        if (word.isEmpty()) {
          break;
        }
        
        searchWords.add(word.toLowerCase());
      }
      
      if (searchWords.isEmpty()) {
        System.out.println("No words provided for search. Exiting...");
        return;
      }
      
      // Perform both search algorithms and compare performance
      System.out.printf("%nPerforming search algorithm comparison...%n");
      
      // Build inverted index (this is a one-time cost)
      System.out.println("Building inverted index...");
      InvertedIndexSearcher invertedIndexSearcher = new InvertedIndexSearcher(fileContent);
      
      // Create function wrappers for performance measurement
      Function<List<String>, Map<String, Integer>> regexSearchFunction = 
          words -> RegExSearcher.searchWords(fileContent, words);
      
      Function<List<String>, Map<String, Integer>> invertedIndexSearchFunction = 
          words -> invertedIndexSearcher.searchWords(words);
      
      // Measure performance with multiple iterations for accuracy
      final int iterations = 10;
      System.out.printf("Running %d iterations for accurate timing...%n", iterations);
      
      // Measure RegEx search time
      long regexTime = PerformanceMeasurer.measureSearchTimeWithIterations(
          regexSearchFunction, fileContent, searchWords, iterations);
      
      // Measure Inverted Index search time
      long invertedIndexTime = PerformanceMeasurer.measureSearchTimeWithIterations(
          invertedIndexSearchFunction, fileContent, searchWords, iterations);
      
      // Perform actual searches to get results
      Map<String, Integer> regexResults = RegExSearcher.searchWords(fileContent, searchWords);
      Map<String, Integer> invertedIndexResults = invertedIndexSearcher.searchWords(searchWords);
      
      // Track search history for each word
      for (String word : searchWords) {
        SearchHistory.addSearchWord(word);
      }
      
      // Display results and performance comparison
      System.out.printf("%n%s%n", "=".repeat(80));
      System.out.println("SEARCH ALGORITHM COMPARISON RESULTS");
      System.out.println("=".repeat(80));
      
      System.out.printf("%-20s | %-15s | %-15s | %-15s%n", 
                        "Word", "RegEx Count", "Index Count", "Search History");
      System.out.println("-".repeat(80));
      
      for (String word : searchWords) {
        Integer regexCount = regexResults.get(word);
        Integer indexCount = invertedIndexResults.get(word);
        Integer searchCount = SearchHistory.getSearchCount(word);
        System.out.printf("%-20s | %-15d | %-15d | %-15d%n",
                          word, regexCount, indexCount, searchCount);
      }
      
      System.out.println("=".repeat(80));
      System.out.println("PERFORMANCE COMPARISON");
      System.out.println("=".repeat(80));
      
      System.out.printf("RegEx Search Time (avg over %d runs): %s%n", 
                        iterations, PerformanceMeasurer.formatTime(regexTime));
      System.out.printf("Inverted Index Search Time (avg over %d runs): %s%n", 
                        iterations, PerformanceMeasurer.formatTime(invertedIndexTime));
      
      // Output timing data for manual plotting
      System.out.println("=".repeat(80));
      System.out.println("TIMING DATA FOR PLOTTING");
      System.out.println("=".repeat(80));
      System.out.println("Word,RegEx_Time_ns,InvertedIndex_Time_ns");
      for (String word : searchWords) {
        // Measure individual word search times
        List<String> singleWord = List.of(word);
        long regexWordTime = PerformanceMeasurer.measureSearchTimeWithIterations(
            regexSearchFunction, fileContent, singleWord, iterations);
        long indexWordTime = PerformanceMeasurer.measureSearchTimeWithIterations(
            invertedIndexSearchFunction, fileContent, singleWord, iterations);
        
        System.out.printf("%s,%d,%d%n", word, regexWordTime, indexWordTime);
      }
      
      System.out.println("=".repeat(80));
      
      // Save search history
      SearchHistory.saveHistory();
    }
  }
}
