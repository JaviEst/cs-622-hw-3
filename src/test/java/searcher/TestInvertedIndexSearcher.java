package searcher;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import org.junit.Assert;
import searcher.InvertedIndexSearcher;

public class TestInvertedIndexSearcher {

  @Test
  public void testBasicSearch() {
    String text = "The quick brown fox jumps over the lazy dog. The fox is quick.";
    InvertedIndexSearcher searcher = new InvertedIndexSearcher(text);
    
    List<String> words = Arrays.asList("the", "fox", "quick");
    Map<String, Integer> results = searcher.searchWords(words);
    
    Assert.assertEquals(Integer.valueOf(3), results.get("the"));
    Assert.assertEquals(Integer.valueOf(2), results.get("fox"));
    Assert.assertEquals(Integer.valueOf(2), results.get("quick"));
  }
  
  @Test
  public void testCaseInsensitiveSearch() {
    String text = "The Quick Brown Fox JUMPS over the lazy dog.";
    InvertedIndexSearcher searcher = new InvertedIndexSearcher(text);
    
    List<String> words = Arrays.asList("the", "quick", "fox");
    Map<String, Integer> results = searcher.searchWords(words);
    
    Assert.assertEquals(Integer.valueOf(2), results.get("the"));
    Assert.assertEquals(Integer.valueOf(1), results.get("quick"));
    Assert.assertEquals(Integer.valueOf(1), results.get("fox"));
  }
  
  @Test
  public void testNonExistentWords() {
    String text = "The quick brown fox jumps over the lazy dog.";
    InvertedIndexSearcher searcher = new InvertedIndexSearcher(text);
    
    List<String> words = Arrays.asList("elephant", "tiger");
    Map<String, Integer> results = searcher.searchWords(words);
    
    Assert.assertEquals(Integer.valueOf(0), results.get("elephant"));
    Assert.assertEquals(Integer.valueOf(0), results.get("tiger"));
  }
  
  @Test
  public void testIndexStatistics() {
    String text = "The quick brown fox jumps over the lazy dog. The fox is quick.";
    InvertedIndexSearcher searcher = new InvertedIndexSearcher(text);
    
    // Should have 9 unique words
    Assert.assertEquals(9, searcher.getIndexSize());
    
    // Total word count should be 13
    Assert.assertEquals(13, searcher.getTotalWordCount());
  }
  
  @Test
  public void testEmptyText() {
    String text = "";
    InvertedIndexSearcher searcher = new InvertedIndexSearcher(text);
    
    List<String> words = Arrays.asList("test");
    Map<String, Integer> results = searcher.searchWords(words);
    
    Assert.assertEquals(Integer.valueOf(0), results.get("test"));
    Assert.assertEquals(0, searcher.getIndexSize());
    Assert.assertEquals(0, searcher.getTotalWordCount());
  }
}
