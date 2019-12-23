package model;

public class MovieUpVO {
	private String MovieID;
	private String MovieName;
	private String MoviewTime;
	private  String julgary;
	private String filedata;
	private int viewAge;
	public MovieUpVO(String movieID, String movieName, String moviewTime, String julgary, String filedata,
			int viewAge) {
		super();
		MovieID = movieID;
		MovieName = movieName;
		MoviewTime = moviewTime;
		this.julgary = julgary;
		this.filedata = filedata;
		this.viewAge = viewAge;
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
	public String getJulgary() {
		return julgary;
	}
	public void setJulgary(String julgary) {
		this.julgary = julgary;
	}
	public String getFiledata() {
		return filedata;
	}
	public void setFiledata(String filedata) {
		this.filedata = filedata;
	}
	public int getViewAge() {
		return viewAge;
	}
	public void setViewAge(int viewAge) {
		this.viewAge = viewAge;
	}
	
	
}
