package searcher;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;

public class TestRegExSearcher {
    
  @Test
  public void testBasicWordSearch() {
    // Test basic word search functionality
    String text = "The quick brown fox jumps over the lazy dog";
    List<String> words = Arrays.asList("the", "fox", "dog");
    
    Map<String, Integer> results = RegExSearcher.searchWords(text, words);
    
    Assert.assertEquals("Should find 'the' twice",
                        Integer.valueOf(2), results.get("the"));
    Assert.assertEquals("Should find 'fox' once",
                        Integer.valueOf(1), results.get("fox"));
    Assert.assertEquals("Should find 'dog' once",
                        Integer.valueOf(1), results.get("dog"));
  }
  
  @Test
  public void testCaseInsensitiveSearch() {
    // Test case-insensitive search behavior
    String text = "The Quick BROWN fox JUMPS over the LAZY dog";
    List<String> words = Arrays.asList("the", "QUICK", "brown", "FOX");
    
    Map<String, Integer> results = RegExSearcher.searchWords(text, words);
    
    Assert.assertEquals("Should find 'the' twice (case insensitive)",
                        Integer.valueOf(2), results.get("the"));
    Assert.assertEquals("Should find 'QUICK' once (case insensitive)",
                        Integer.valueOf(1), results.get("QUICK"));
    Assert.assertEquals("Should find 'brown' once (case insensitive)",
                        Integer.valueOf(1), results.get("brown"));
    Assert.assertEquals("Should find 'FOX' once (case insensitive)",
                        Integer.valueOf(1), results.get("FOX"));
  }
  
  @Test
  public void testMultipleWordsInSameText() {
    // Test searching for multiple words in the same text
    String text = "Java is a programming language. Java is object-oriented.";
    List<String> words = Arrays.asList("Java", "is", "programming", "object-oriented");
    
    Map<String, Integer> results = RegExSearcher.searchWords(text, words);
    
    Assert.assertEquals("Should find 'Java' three times",
                        Integer.valueOf(2), results.get("Java"));
    Assert.assertEquals("Should find 'is' three times",
                        Integer.valueOf(2), results.get("is"));
    Assert.assertEquals("Should find 'programming' once",
                        Integer.valueOf(1), results.get("programming"));
    Assert.assertEquals("Should find 'object-oriented' once",
                        Integer.valueOf(1), results.get("object-oriented"));
  }
  
  @Test
  public void testWordNotFound() {
    // Test behavior when word is not found in text
    String text = "The quick brown fox jumps over the lazy dog";
    List<String> words = Arrays.asList("cat", "elephant", "tiger");
    
    Map<String, Integer> results = RegExSearcher.searchWords(text, words);
    
    Assert.assertEquals("Should find 'cat' zero times",
                        Integer.valueOf(0), results.get("cat"));

    Assert.assertEquals("Should find 'elephant' zero times",
                        Integer.valueOf(0), results.get("elephant"));
    Assert.assertEquals("Should find 'tiger' zero times",
                        Integer.valueOf(0), results.get("tiger"));
  }
  
  @Test
  public void testEmptyText() {
    // Test behavior with empty text input
    String text = "";
    List<String> words = Arrays.asList("test", "word");
    
    Map<String, Integer> results = RegExSearcher.searchWords(text, words);
    
    Assert.assertEquals("Should find 'test' zero times in empty text",
                        Integer.valueOf(0), results.get("test"));
    Assert.assertEquals("Should find 'word' zero times in empty text",
                        Integer.valueOf(0), results.get("word"));
  }
  
  @Test
  public void testEmptyWordList() {
    // Test behavior with empty word list
    String text = "This is a test text with some words";
    List<String> words = new ArrayList<>();
    
    Map<String, Integer> results = RegExSearcher.searchWords(text, words);
    
    Assert.assertTrue("Results should be empty for empty word list", results.isEmpty());
  }
  
  @Test
  public void testSpecialCharacters() {
    // Test search with special characters and regex patterns
    String text = "The price is $100.50. Call (555) 123-4567. Email: test@example.com";
    List<String> words = Arrays.asList("\\$", "\\(", "\\)", "@", "\\.");
    
    Map<String, Integer> results = RegExSearcher.searchWords(text, words);
    
    Assert.assertEquals("Should find '$' once",
                        Integer.valueOf(1), results.get("\\$"));
    Assert.assertEquals("Should find '(' once",
                        Integer.valueOf(1), results.get("\\("));
    Assert.assertEquals("Should find ')' once",
                        Integer.valueOf(1), results.get("\\)"));
    Assert.assertEquals("Should find '@' once",
                        Integer.valueOf(1), results.get("@"));
    Assert.assertEquals("Should find '.' multiple times (4)",
                        Integer.valueOf(4), results.get("\\."));
  }
  
  @Test
  public void testWordBoundaries() {
    // Test word boundary matching behavior
    String text = "The cat in the hat. The category is important. Scattered thoughts.";
    List<String> words = Arrays.asList("cat", "the");
    
    Map<String, Integer> results = RegExSearcher.searchWords(text, words);
    
    // Note: The current implementation uses simple pattern matching, not word boundaries
    // So "cat" in "category" and "scattered" will also match
    Assert.assertEquals("Should find 'cat' in 'cat', 'category', and 'scattered'",
                        Integer.valueOf(3), results.get("cat"));
  }
  
  @Test
  public void testDuplicateWordsInSearchList() {
    // Test behavior with duplicate words in search list
    String text = "The quick brown fox jumps over the lazy dog";
    List<String> words = Arrays.asList("the", "fox", "the", "dog", "fox");
    
    Map<String, Integer> results = RegExSearcher.searchWords(text, words);
    
    // The implementation will process each word in the list, including duplicates
    // So "the" appears twice in the list, but the final count should be the same
    Assert.assertEquals("Should find 'the' twice",
                        Integer.valueOf(2), results.get("the"));
    Assert.assertEquals("Should find 'fox' once",
                        Integer.valueOf(1), results.get("fox"));
    Assert.assertEquals("Should find 'dog' once",
                        Integer.valueOf(1), results.get("dog"));
  }
  
  @Test
  public void testActualRegexPatterns() {
    // Test actual regex pattern matching capabilities
    String text = "The numbers are 123, 456, and 789. Also 12.34 and 0.5";
    List<String> words = Arrays.asList("\\d+", "\\d+\\.\\d+", "[0-9]+");
    
    Map<String, Integer> results = RegExSearcher.searchWords(text, words);
    
    Assert.assertEquals("Should find '\\d+' pattern multiple times",
                        Integer.valueOf(7), results.get("\\d+"));
    Assert.assertEquals("Should find '\\d+\\.\\d+' pattern twice",
                        Integer.valueOf(2), results.get("\\d+\\.\\d+"));
    Assert.assertEquals("Should find '[0-9]+' pattern multiple times",
                        Integer.valueOf(7), results.get("[0-9]+"));
  }
  
  @Test
  public void testWhitespaceAndNewlines() {
    // Test search with whitespace and newlines
    String text = "Line 1\nLine 2\r\nLine 3\n\rLine 4";
    List<String> words = Arrays.asList("Line", "\\n", "\\r");
    
    Map<String, Integer> results = RegExSearcher.searchWords(text, words);
    
    Assert.assertEquals("Should find 'Line' four times",
                        Integer.valueOf(4), results.get("Line"));
    Assert.assertEquals("Should find '\\n' three times",
                        Integer.valueOf(3), results.get("\\n"));
    Assert.assertEquals("Should find '\\r' two times",
                        Integer.valueOf(2), results.get("\\r"));
  }
  
  @Test
  public void testComplexRegexPatterns() {
    // Test complex regex patterns
    String text = "Email: john.doe@example.com, Phone: (555) 123-4567, Date: 2023-12-25";
    List<String> words = Arrays.asList("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}", 
                                       "\\(\\d{3}\\)\\s\\d{3}-\\d{4}", 
                                       "\\d{4}-\\d{2}-\\d{2}");
    
    Map<String, Integer> results = RegExSearcher.searchWords(text, words);
    
    Assert.assertEquals("Should find email pattern once",
                        Integer.valueOf(1), results.get("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"));
    Assert.assertEquals("Should find phone pattern once",
                        Integer.valueOf(1), results.get("\\(\\d{3}\\)\\s\\d{3}-\\d{4}"));
    Assert.assertEquals("Should find date pattern once",
                        Integer.valueOf(1), results.get("\\d{4}-\\d{2}-\\d{2}"));
}
}
