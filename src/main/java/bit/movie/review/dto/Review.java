package bit.movie.review.dto;

public class Review {
	private int id;
	private int code;
	private String writer;
	private String contents;
	private String created;
	private int starRating;
	private String movieName;
	private String corp;
	
	public String getCorp() {
		return corp;
	}
	public void setCorp(String corp) {
		this.corp = corp;
	}
	public String getMovieName() {
		return movieName;
	}
	public void setMovieName(String movieName) {
		this.movieName = movieName;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public int getStarRating() {
		return starRating;
	}
	public void setStarRating(int starRating) {
		this.starRating = starRating;
	}
	@Override
	public String toString() {
		return "Review [id=" + id + ", code=" + code + ", writer=" + writer + ", contents=" + contents + ", created="
				+ created + ", starRating=" + starRating + "]";
	}
	
	
}
