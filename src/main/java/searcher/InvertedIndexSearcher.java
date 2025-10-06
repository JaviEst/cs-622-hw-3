package searcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Inverted Index search implementation using traditional Java approaches.
 * This class builds an inverted index from the text and uses it for efficient word searching.
 */
public class InvertedIndexSearcher {
    
  private final Map<String, Integer> invertedIndex;
  
  /**
   * Constructor that builds the inverted index from the given text.
   * 
   * @param text the text to build the index from
   */
  public InvertedIndexSearcher(String text) {
    this.invertedIndex = buildInvertedIndex(text);
  }
    
  /**
   * Builds an inverted index from the given text.
   * 
   * @param text the text to index
   * @return a map where keys are words and values are their frequencies
   */
  private Map<String, Integer> buildInvertedIndex(String text) {
    Map<String, Integer> wordFrequency = new HashMap<>();
    
    // Convert to lowercase and split on non-word characters
    String[] words = text.toLowerCase().split("\\W+");
    
    // Count frequency of each word
    for (String word : words) {
      if (!word.isEmpty()) {
        wordFrequency.merge(word, 1, Integer::sum);
      }
    }
    
    return wordFrequency;
  }
    
  /**
   * Search for word occurrences using the inverted index.
   * 
   * @param words the list of words to search for
   * @return a map containing the word and the number of occurrences
   */
  public Map<String, Integer> searchWords(List<String> words) {
    Map<String, Integer> results = new HashMap<>();
    
    for (String word : words) {
      String lowercaseWord = word.toLowerCase();
      results.put(lowercaseWord, invertedIndex.getOrDefault(lowercaseWord, 0));
    }
    
    return results;
  }
    
  /**
   * Get the total number of unique words in the index.
   * 
   * @return the size of the inverted index
   */
  public int getIndexSize() {
    return invertedIndex.size();
  }
    
  /**
   * Get the total number of words (including duplicates) in the original text.
   * 
   * @return the sum of all word frequencies
   */
  public int getTotalWordCount() {
    int totalCount = 0;
    for (Integer frequency : invertedIndex.values()) {
      totalCount += frequency;
    }
    return totalCount;
  }
}
