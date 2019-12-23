package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import model.MovieUpVO;
import model.MovieViewVo;

public class MovieInfoController implements Initializable{
	@FXML AnchorPane MovieInfoAnchroPane;
	@FXML Button btnMovieCarMove;
	@FXML Button btnMainMove;
	@FXML TextArea julgery;
	@FXML ImageView PosterImage;
	ArrayList<MovieUpVO> list=null;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String name=MovieMainController.selectStudent.get(0).getMovieID();
		list=MovieDAO.getMovieJugery(name);
		if(list==null) {
			System.out.println("안들어가있니?");
			
		}
		//자신이 고른 이미지를 확인
		PosterImage.setImage(MovieMainController.localImage);
		//자신이 고른 영화의 줄거리 가져오기
		julgery.setText(list.get(0).getJulgary());
		//확인 버튼을 누르고 예매 직전 창으로 간다.
		btnMovieCarMove.setOnMouseClicked(e->{
			try {
				StackPane stackPane=(StackPane)btnMovieCarMove.getScene().getRoot();
				AnchorPane anchorPane=FXMLLoader.load(getClass().getResource("/view/CarMovie.fxml"));
				stackPane.getChildren().add(anchorPane);
				StackPane stackPane1=(StackPane) julgery.getScene().getRoot();
				stackPane1.getChildren().remove(MovieInfoAnchroPane);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		//이전 버튼을 누르고 메인창으로
		btnMainMove.setOnAction(e->{
			StackPane stackPane=(StackPane) julgery.getScene().getRoot();
			stackPane.getChildren().remove(MovieInfoAnchroPane);
		});
	}

}
