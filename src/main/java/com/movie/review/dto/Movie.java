package com.movie.review.dto;

public class Movie {
  private String movieName;
  private String posterImgSrc;
  private String movieAge;
  private Double starRating;
  private String audience;
  private String ticketing;
  
  
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
