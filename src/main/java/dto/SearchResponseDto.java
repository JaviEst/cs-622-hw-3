package dto;

import java.util.List;


public class SearchResponseDto {
  private List<SearchResultDto> results;
  private List<PerformanceDto> performanceData;
  private long totalRegexTime;
  private long totalInvertedIndexTime;
  private String totalRegexTimeFormatted;
  private String totalInvertedIndexTimeFormatted;
  private int iterations;
  
  public SearchResponseDto() {
  }
  
  public List<SearchResultDto> getResults() {
    return results;
  }
  
  public void setResults(List<SearchResultDto> results) {
    this.results = results;
  }
  
  public List<PerformanceDto> getPerformanceData() {
    return performanceData;
  }
  
  public void setPerformanceData(List<PerformanceDto> performanceData) {
    this.performanceData = performanceData;
  }
  
  public long getTotalRegexTime() {
    return totalRegexTime;
  }
  
  public void setTotalRegexTime(long totalRegexTime) {
    this.totalRegexTime = totalRegexTime;
  }
  
  public long getTotalInvertedIndexTime() {
    return totalInvertedIndexTime;
  }
  
  public void setTotalInvertedIndexTime(long totalInvertedIndexTime) {
    this.totalInvertedIndexTime = totalInvertedIndexTime;
  }
  
  public String getTotalRegexTimeFormatted() {
    return totalRegexTimeFormatted;
  }
  
  public void setTotalRegexTimeFormatted(String totalRegexTimeFormatted) {
    this.totalRegexTimeFormatted = totalRegexTimeFormatted;
  }
  
  public String getTotalInvertedIndexTimeFormatted() {
    return totalInvertedIndexTimeFormatted;
  }
  
  public void setTotalInvertedIndexTimeFormatted(String totalInvertedIndexTimeFormatted) {
    this.totalInvertedIndexTimeFormatted = totalInvertedIndexTimeFormatted;
  }
  
  public int getIterations() {
    return iterations;
  }
  
  public void setIterations(int iterations) {
    this.iterations = iterations;
  }
}
