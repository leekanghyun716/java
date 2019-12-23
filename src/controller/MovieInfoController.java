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
			System.out.println("�ȵ��ִ�?");
			
		}
		//�ڽ��� �� �̹����� Ȯ��
		PosterImage.setImage(MovieMainController.localImage);
		//�ڽ��� �� ��ȭ�� �ٰŸ� ��������
		julgery.setText(list.get(0).getJulgary());
		//Ȯ�� ��ư�� ������ ���� ���� â���� ����.
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
		//���� ��ư�� ������ ����â����
		btnMainMove.setOnAction(e->{
			StackPane stackPane=(StackPane) julgery.getScene().getRoot();
			stackPane.getChildren().remove(MovieInfoAnchroPane);
		});
	}

}
