package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import model.UserBooks;

public class MovieTicketController implements Initializable{
	@FXML AnchorPane TIcketAnchor;
	@FXML Label LabelMovie;
	@FXML Label LabelMovieTime;
	@FXML Button btnMainMove;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//예매한 영화 정보를 가져오기
		String UserID=RootController.name;
		String MovieID=MovieMainController.selectStudent.get(0).getMovieID();
		String MovieTime=MovieMainController.selectStudent.get(0).getMoviewTime();
		String MovieName=MovieMainController.selectStudent.get(0).getMovieName();
		int Price=22000;
		UserBooks books=new UserBooks(UserID, MovieID, MovieTime,MovieName,Price);
		//예약한 정보를 데이터에 올린다.
		LabelMovie.setText(MovieMainController.selectStudent.get(0).getMovieID());
		LabelMovieTime.setText(MovieMainController.selectStudent.get(0).getMoviewTime());
		btnMainMove.setOnAction(e->{
			MovieDAO.UpdateUserGoods(books);
			
			StackPane stackPane=(StackPane) btnMainMove.getScene().getRoot();
			stackPane.getChildren().remove(TIcketAnchor);
			
		});
		
	}

}
