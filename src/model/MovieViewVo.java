package model;

public class MovieViewVo {
	private String MovieID;
	private String MovieName;
	private String MoviewTime;
	private int MovieTotalPrice;
	private int MovieTotalView;
	private String fileName;
	
	
	public MovieViewVo(String movieID, String movieName, String moviewTime, int movieTotalPrice, int movieTotalView,
			String fileName) {
		super();
		MovieID = movieID;
		MovieName = movieName;
		MoviewTime = moviewTime;
		MovieTotalPrice = movieTotalPrice;
		MovieTotalView = movieTotalView;
		this.fileName = fileName;
	}
	public MovieViewVo(String movieID, String movieName, String moviewTime, int movieTotalPrice, int movieTotalView) {
		super();
		MovieID = movieID;
		MovieName = movieName;
		MoviewTime = moviewTime;
		MovieTotalPrice = movieTotalPrice;
		MovieTotalView = movieTotalView;
	}
	public String getMovieID() {
		return MovieID;
	}
	public void setMovieID(String movieID) {
		MovieID = movieID;
	}
	public String getMovieName() {
		return MovieName;
	}
	public void setMovieName(String movieName) {
		MovieName = movieName;
	}
	public String getMoviewTime() {
		return MoviewTime;
	}
	public void setMoviewTime(String moviewTime) {
		MoviewTime = moviewTime;
	}
	public int getMovieTotalPrice() {
		return MovieTotalPrice;
	}
	public void setMovieTotalPrice(int movieTotalPrice) {
		MovieTotalPrice = movieTotalPrice;
	}
	public int getMovieTotalView() {
		return MovieTotalView;
	}
	public void setMovieTotalView(int movieTotalView) {
		MovieTotalView = movieTotalView;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
	//(MovieID,MovieName,MoviewTime,MovieTotalPrice,MovieTotalView)
}
