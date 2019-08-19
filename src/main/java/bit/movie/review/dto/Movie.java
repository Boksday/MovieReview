package bit.movie.review.dto;

import java.sql.Date;

public class Movie {
  private int id;
  private int codeNaver;
  private int codeDaum;
  
  private String movieName;
  private String posterImgSrc;
  private String movieAge;
  private Double starRating;
  private String audience;
  private String ticketing;
  
  private Date date;
  
  public Date getDate() {
    return date;
  }
  public void setDate(Date date) {
    this.date = date;
  }
  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }
  public int getCodeNaver() {
    return codeNaver;
  }
  public void setCodeNaver(int codeNaver) {
    this.codeNaver = codeNaver;
  }
  public int getCodeDaum() {
    return codeDaum;
  }
  public void setCodeDaum(int codeDaum) {
    this.codeDaum = codeDaum;
  }
  public String getTicketing() {
    return ticketing;
  }
  public void setTicketing(String ticketing) {
    this.ticketing = ticketing;
  }
  public String getAudience() {
    return audience;
  }
  public void setAudience(String audience) {
    this.audience = audience;
  }
  public Double getStarRating() {
    return starRating;
  }
  public void setStarRating(Double starRating) {
    this.starRating = starRating;
  }
  public String getMovieAge() {
    return movieAge;
  }
  public void setMovieAge(String movieAge) {
    this.movieAge = movieAge;
  }
  public String getMovieName() {
    return movieName;
  }
  public void setMovieName(String movieName) {
    this.movieName = movieName;
  }
  public String getPosterImgSrc() {
    return posterImgSrc;
  }
  public void setPosterImgSrc(String posterImgSrc) {
    this.posterImgSrc = posterImgSrc;
  }
  
  
}
