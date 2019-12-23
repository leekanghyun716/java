package model;

public class MovieENG {
	private String MovieID;
	private String MovieTime;
	public MovieENG(String movieID, String movieTime) {
		super();
		MovieID = movieID;
		MovieTime = movieTime;
	}
	public String getMovieID() {
		return MovieID;
	}
	public void setMovieID(String movieID) {
		MovieID = movieID;
	}
	public String getMovieTime() {
		return MovieTime;
	}
	public void setMovieTime(String movieTime) {
		MovieTime = movieTime;
	}
	
}
