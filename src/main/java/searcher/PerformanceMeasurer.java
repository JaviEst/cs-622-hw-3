package searcher;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for measuring performance of different search algorithms.
 */
public class PerformanceMeasurer {
    
  /**
   * Measures the execution time of a search function.
   * 
   * @param searchFunction the function to measure
   * @param text the text to search in
   * @param words the words to search for
   * @return the execution time in nanoseconds
   */
  public static long measureSearchTime(Function<List<String>,
                                       Map<String, Integer>> searchFunction,
                                       String text, List<String> words) {
    long startTime = System.nanoTime();
    searchFunction.apply(words);
    long endTime = System.nanoTime();
    return endTime - startTime;
  }
  
  /**
   * Measures the execution time of a search function with multiple iterations for better accuracy.
   * 
   * @param searchFunction the function to measure
   * @param text the text to search in
   * @param words the words to search for
   * @param iterations the number of iterations to run
   * @return the average execution time in nanoseconds
   */
  public static long measureSearchTimeWithIterations(Function<List<String>,
                                                     Map<String, Integer>> searchFunction,
                                                     String text, List<String> words,
                                                     int iterations) {
    long totalTime = 0;
    
    for (int i = 0; i < iterations; i++) {
      totalTime += measureSearchTime(searchFunction, text, words);
    }
    
    return totalTime / iterations;
  }
  
  /**
   * Converts nanoseconds to milliseconds.
   * 
   * @param nanoseconds the time in nanoseconds
   * @return the time in milliseconds
   */
  public static double nanosecondsToMilliseconds(long nanoseconds) {
    return nanoseconds / 1_000_000.0;
  }
  
  /**
   * Formats time for display.
   * 
   * @param nanoseconds the time in nanoseconds
   * @return formatted time string
   */
  public static String formatTime(long nanoseconds) {
    if (nanoseconds < 1_000) {
      return nanoseconds + " ns";
    } else if (nanoseconds < 1_000_000) {
      return String.format("%.2f μs", nanoseconds / 1_000.0);
    } else if (nanoseconds < 1_000_000_000) {
      return String.format("%.2f ms", nanoseconds / 1_000_000.0);
    } else {
      return String.format("%.2f s", nanoseconds / 1_000_000_000.0);
    }
  }
}
