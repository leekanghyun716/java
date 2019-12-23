package model;

public class UserBooks {
	private String userID;
	private String MovieID;
	private String MovieTime;
	private String MovieName;
	private int MoviePrice;
	
	
	
	public UserBooks(String userID, String movieID, String movieTime, String movieName, int moviePrice) {
		super();
		this.userID = userID;
		MovieID = movieID;
		MovieTime = movieTime;
		setMovieName(movieName);
		MoviePrice = moviePrice;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
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
	public int getMoviePrice() {
		return MoviePrice;
	}
	public void setMoviePrice(int moviePrice) {
		MoviePrice = moviePrice;
	}
	public String getMovieName() {
		return MovieName;
	}
	public void setMovieName(String movieName) {
		MovieName = movieName;
	}
	
	
}
