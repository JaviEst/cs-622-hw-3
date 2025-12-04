package dto;


public class AnimeDto {
  private int malId;
  private String title;
  private String titleEnglish;
  private String imageUrl;
  private String type;
  private Integer episodes;
  private String status;
  private Double score;
  private String synopsis;
  private String year;
  private String rating;
  
  public AnimeDto() {
  }
  
  public int getMalId() {
    return malId;
  }
  
  public void setMalId(int malId) {
    this.malId = malId;
  }
  
  public String getTitle() {
    return title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getTitleEnglish() {
    return titleEnglish;
  }
  
  public void setTitleEnglish(String titleEnglish) {
    this.titleEnglish = titleEnglish;
  }
  
  public String getImageUrl() {
    return imageUrl;
  }
  
  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
  
  public String getType() {
    return type;
  }
  
  public void setType(String type) {
    this.type = type;
  }
  
  public Integer getEpisodes() {
    return episodes;
  }
  
  public void setEpisodes(Integer episodes) {
    this.episodes = episodes;
  }
  
  public String getStatus() {
    return status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public Double getScore() {
    return score;
  }
  
  public void setScore(Double score) {
    this.score = score;
  }
  
  public String getSynopsis() {
    return synopsis;
  }
  
  public void setSynopsis(String synopsis) {
    this.synopsis = synopsis;
  }
  
  public String getYear() {
    return year;
  }
  
  public void setYear(String year) {
    this.year = year;
  }
  
  public String getRating() {
    return rating;
  }
  
  public void setRating(String rating) {
    this.rating = rating;
  }
}
