package model;

public class UserChartVO {
	private String MovieName;
	private int MoviePrice;
	public UserChartVO(String movieName, int moviePrice) {
		super();
		MovieName = movieName;
		MoviePrice = moviePrice;
	}
	public String getMovieName() {
		return MovieName;
	}
	public void setMovieName(String movieName) {
		MovieName = movieName;
	}
	public int getMoviePrice() {
		return MoviePrice;
	}
	public void setMoviePrice(int moviePrice) {
		MoviePrice = moviePrice;
	}
	@Override
	public String toString() {
		return "UserChartVO [MovieName=" + MovieName + ", MoviePrice=" + MoviePrice + "]";
	}
	
}
