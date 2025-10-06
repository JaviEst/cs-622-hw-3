package searcher;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.junit.Assert;

import org.junit.Test;

public class TestPerformanceMeasurer {

  // Create a simple search lambda function that just returns counts
  Function<List<String>, Map<String, Integer>> simpleSearchFunction = 
  wordsList -> {
    Map<String, Integer> result = new java.util.HashMap<>();
    for (String word : wordsList) {
        result.put(word, 1);
    }
    return result;
  };

  @Test
  public void testMeasureSearchTime() {
    String text = "The quick brown fox jumps over the lazy dog.";
    List<String> words = Arrays.asList("the", "fox");
    
    long executionTime = PerformanceMeasurer.measureSearchTime(simpleSearchFunction, text, words);
    
    Assert.assertTrue("Execution time should be positive", executionTime >= 0);
  }
  
  @Test
  public void testMeasureSearchTimeWithIterations() {
    String text = "The quick brown fox jumps over the lazy dog.";
    List<String> words = Arrays.asList("the", "fox");
    
    int iterations = 5;
    long avgTime = PerformanceMeasurer.measureSearchTimeWithIterations(
        simpleSearchFunction, text, words, iterations);
    
    Assert.assertTrue("Average execution time should be positive", avgTime >= 0);
  }
  
  @Test
  public void testTimeFormatting() {
    // Test nanoseconds
    String nsResult = PerformanceMeasurer.formatTime(500);
    Assert.assertTrue("Should format nanoseconds correctly", nsResult.contains("ns"));
    
    // Test microseconds
    String usResult = PerformanceMeasurer.formatTime(5000);
    Assert.assertTrue("Should format microseconds correctly", usResult.contains("μs"));
    
    // Test milliseconds
    String msResult = PerformanceMeasurer.formatTime(5000000);
    Assert.assertTrue("Should format milliseconds correctly", msResult.contains("ms"));
    
    // Test seconds
    String sResult = PerformanceMeasurer.formatTime(5000000000L);
    Assert.assertTrue("Should format seconds correctly", sResult.contains("s"));
  }
  
  @Test
  public void testNanosecondsToMilliseconds() {
    double result = PerformanceMeasurer.nanosecondsToMilliseconds(1000000);
    Assert.assertEquals(1.0, result, 0.001);
    
    result = PerformanceMeasurer.nanosecondsToMilliseconds(500000);
    Assert.assertEquals(0.5, result, 0.001);
  }
}
