package bit.movie.review.dto;

public class Naver {
	private int code;
	private double starRating;
	private String movieName;
	private String date;
	
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public double getStarRating() {
		return starRating;
	}
	public void setStarRating(double starRating) {
		this.starRating = starRating;
	}
	
	@Override
	public String toString() {
		return "Naver [code=" + code + ", starRating=" + starRating + ", movieName=" + movieName + ", date=" + date
				+ "]";
	}
	
}
