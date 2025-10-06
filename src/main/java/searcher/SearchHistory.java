package searcher;

import file.FileHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SearchHistory {
  private static final String SEARCH_HISTORY_FILE_NAME = "search-history.txt";
  private static final Map<String, Integer> searchCounts = new HashMap<>();

  /**
   * Load search history from file on startup.
   */
  public static void loadHistory() {
    try {
      Scanner scanner = new Scanner(new File(SEARCH_HISTORY_FILE_NAME));

      while (scanner.hasNextLine()) {
        String line = scanner.nextLine().trim();
        if (!line.isEmpty()) {
          String[] parts = line.split(":", 2);
          if (parts.length == 2) {
            String word = parts[0].trim();
            int count = Integer.parseInt(parts[1].trim());

            searchCounts.put(word, count);
          }
        }
      }
      scanner.close();
      System.out.println("Search history loaded from file.");
    } catch (FileNotFoundException e) {
      System.out.println("No previous search history found. Starting fresh.");
    } catch (NumberFormatException e) {
      System.err.println("Error parsing search history file. Starting fresh.");
    }
  }

  /**
   * Add a search word and increment its count.
   * 
   * @param word the word which has been searched
   */
  public static void addSearchWord(String word) {
    searchCounts.put(word, searchCounts.getOrDefault(word, 0) + 1);
  }

  /**
   * Get the search count for a specific word.
   * 
   * @param word the word to check if it has been searched before
   */
  public static int getSearchCount(String word) {
    return searchCounts.getOrDefault(word, 0);
  }

  /**
   * Save search history to file.
   */
  public static void saveHistory() {
    StringBuilder historyData = new StringBuilder();
    for (Map.Entry<String, Integer> entry : searchCounts.entrySet()) {
      historyData.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
    }
    FileHandler.saveToFile(historyData.toString(), SEARCH_HISTORY_FILE_NAME);
    System.out.println("Search history saved to file.");
  }
}
