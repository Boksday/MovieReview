package bit.movie.review.dto;

public class Movie {
	private String movieName;//영화이름 
	private String posterImgSrc;//포스터 
	private String movieAge;//제한연령 
	private String audience; //누적관객 
	private String ticketing; //예매율 

	private String date; //개봉날짜 
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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

	@Override
	public String toString() {
		return "Movie [movieName=" + movieName + ", posterImgSrc=" + posterImgSrc + ", movieAge=" + movieAge
				+ ", audience=" + audience + ", ticketing=" + ticketing + ", date=" + date + "]";
	}

}
