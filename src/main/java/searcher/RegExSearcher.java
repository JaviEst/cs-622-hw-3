package searcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegExSearcher {

  /**
   * Search for word occurrences in a text.
   * 
   * @param text the text to be used when looking for the words occurrences
   * @param words the list fo words to be searched in the text
   * @return a hasmap containing the word and the number of occurrences of that word in the text
   */
  public static Map<String, Integer> searchWords(String text, List<String> words) {
    Map<String, Integer> results = new HashMap<>();

    for (String word : words) {
      Pattern pattern = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
      Matcher matcher = pattern.matcher(text);

      int count = 0;
      while (matcher.find()) {
        count++;
      }

      results.put(word, count);
    }

    return results;
  }
}

