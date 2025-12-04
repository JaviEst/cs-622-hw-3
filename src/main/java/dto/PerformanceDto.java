package dto;


public class PerformanceDto {
  private String word;
  private long regexTimeNs;
  private long invertedIndexTimeNs;
  private String regexTimeFormatted;
  private String invertedIndexTimeFormatted;
  private double speedupFactor;
  
  public PerformanceDto() {
  }
  
  public PerformanceDto(String word, long regexTimeNs, long invertedIndexTimeNs,
                        String regexTimeFormatted, String invertedIndexTimeFormatted) {
    this.word = word;
    this.regexTimeNs = regexTimeNs;
    this.invertedIndexTimeNs = invertedIndexTimeNs;
    this.regexTimeFormatted = regexTimeFormatted;
    this.invertedIndexTimeFormatted = invertedIndexTimeFormatted;
    
    if (invertedIndexTimeNs > 0) {
      this.speedupFactor = (double) regexTimeNs / invertedIndexTimeNs;
    } else {
      this.speedupFactor = 0;
    }
  }
  
  public String getWord() {
    return word;
  }
  
  public void setWord(String word) {
    this.word = word;
  }
  
  public long getRegexTimeNs() {
    return regexTimeNs;
  }
  
  public void setRegexTimeNs(long regexTimeNs) {
    this.regexTimeNs = regexTimeNs;
  }
  
  public long getInvertedIndexTimeNs() {
    return invertedIndexTimeNs;
  }
  
  public void setInvertedIndexTimeNs(long invertedIndexTimeNs) {
    this.invertedIndexTimeNs = invertedIndexTimeNs;
  }
  
  public String getRegexTimeFormatted() {
    return regexTimeFormatted;
  }
  
  public void setRegexTimeFormatted(String regexTimeFormatted) {
    this.regexTimeFormatted = regexTimeFormatted;
  }
  
  public String getInvertedIndexTimeFormatted() {
    return invertedIndexTimeFormatted;
  }
  
  public void setInvertedIndexTimeFormatted(String invertedIndexTimeFormatted) {
    this.invertedIndexTimeFormatted = invertedIndexTimeFormatted;
  }
  
  public double getSpeedupFactor() {
    return speedupFactor;
  }
  
  public void setSpeedupFactor(double speedupFactor) {
    this.speedupFactor = speedupFactor;
  }
}
