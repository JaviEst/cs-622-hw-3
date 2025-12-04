package dto;


public class SearchResultDto {
  private String word;
  private int regexCount;
  private int invertedIndexCount;
  private int searchHistoryCount;
  
  public SearchResultDto() {
  }
  
  public SearchResultDto(String word, int regexCount, int invertedIndexCount,
                         int searchHistoryCount) {
    this.word = word;
    this.regexCount = regexCount;
    this.invertedIndexCount = invertedIndexCount;
    this.searchHistoryCount = searchHistoryCount;
  }
  
  public String getWord() {
    return word;
  }
  
  public void setWord(String word) {
    this.word = word;
  }
  
  public int getRegexCount() {
    return regexCount;
  }
  
  public void setRegexCount(int regexCount) {
    this.regexCount = regexCount;
  }
  
  public int getInvertedIndexCount() {
    return invertedIndexCount;
  }
  
  public void setInvertedIndexCount(int invertedIndexCount) {
    this.invertedIndexCount = invertedIndexCount;
  }
  
  public int getSearchHistoryCount() {
    return searchHistoryCount;
  }
  
  public void setSearchHistoryCount(int searchHistoryCount) {
    this.searchHistoryCount = searchHistoryCount;
  }
}
