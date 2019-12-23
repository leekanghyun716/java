package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import model.MovieViewVo;

public class CarMovieController implements Initializable {
	@FXML AnchorPane CarAnchorPane;
	@FXML DatePicker CarMovieDP;
	@FXML TableView CarMovieTB;
	@FXML Button btnPreView;
	@FXML Button btnOk;
	static StackPane stackPan1;
	ObservableList<MovieViewVo> data1;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		MovieMainController controller= new MovieMainController();
		//데이터에 테이터를 넣어준다
		data1=FXCollections.observableArrayList();
		MovieTimeTableSetting();
		//자신이 고른 영화 말고 또 어떤영화가 있는지 확인할수있도록만 해주고 이상의 기능은 없다.
		CarMovieDP.setOnAction(e->{
			//DatePiker에 따른 영화 정보 가져오기
			MovieTimeTableInit();
		});
		//데이트 피커에 기본값을 설정하기 위한
		String[] ss=controller.s.split("-");
		CarMovieDP.setPromptText(controller.s);
		//CarMovieDP.setValue(LocalDate.of(Integer.parseInt(ss[0]), Integer.parseInt(ss[1]), Integer.parseInt(ss[2])));
		//영화 날짜와 자신이 선택한 영화를 예매하기 위한 버튼
		btnOk.setOnAction(e->{
			try {
				if(controller.s.equals(CarMovieDP.getValue().toString())) {
					
				}else {
					alertDisplay(1, "경고", "처음에 선택한 영화 날짜와 틀립니다.", "점검요망");
					return;
				}
				StackPane stackPane=(StackPane)btnPreView.getScene().getRoot();
				AnchorPane anchorPane=FXMLLoader.load(getClass().getResource("/view/MovieTicket.fxml"));
				stackPane.getChildren().add(anchorPane);
				StackPane stackPane1=(StackPane) btnPreView.getScene().getRoot();
				stackPane1.getChildren().remove(CarAnchorPane);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		//이전화면으로 돌아가기
		btnPreView.setOnAction(e->{
			try {
			StackPane stackPane1=(StackPane)btnPreView.getScene().getRoot();
				AnchorPane anchorPane=FXMLLoader.load(getClass().getResource("/view/MovieInfo.fxml"));
				stackPane1.getChildren().add(anchorPane);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			StackPane stackPane=(StackPane) btnPreView.getScene().getRoot();
			stackPane.getChildren().remove(CarAnchorPane);
		});
	}
	private void MovieTimeTableSetting() {

		
	      TableColumn colMovieID = new TableColumn("영화아이디");
	      colMovieID.setMaxWidth(300);
	      colMovieID.setPrefWidth(158);
	      colMovieID.setStyle("-fx-alignment: CENTER;");
	      colMovieID.setCellValueFactory(new PropertyValueFactory("MovieID"));

	      TableColumn colMovieTime = new TableColumn("상영시간");
	      colMovieTime.setMaxWidth(200);
	      colMovieTime.setPrefWidth(225);
	      colMovieTime.setStyle("-fx-alignment: CENTER;");
	      colMovieTime.setCellValueFactory(new PropertyValueFactory("MoviewTime"));

	    
	      CarMovieTB.setItems(data1); // 6, 함수새로고침 되는 부분
	      CarMovieTB.getColumns().addAll(colMovieID,colMovieTime);
		
		
	}
	private void MovieTimeTableInit() {
		data1.removeAll(data1);
		ArrayList<MovieViewVo> list = null;
		MovieDAO movieDAO = new MovieDAO();
	      MovieViewVo movieViewVo = null;
	      String date=CarMovieDP.getValue().toString();
	      System.out.println(CarMovieDP.getValue().toString()+"66666666666666");
	      
	      list = MovieDAO.getMovieTime(date);
	     
	      System.out.println( list.get(0).getMovieID());
	      
	      if (list == null) {
	         alertDisplay(1, "경고", "DB 가져오기 오류", "점검요망");
	         return;

	      } else {
	         for (int i = 0; i < list.size(); i++) {
	        	 movieViewVo = list.get(i);
	            data1.add(movieViewVo);
	         }
	      }
	}
	public void alertDisplay(int type, String title, String headerText, String contentText) {

		Alert alert = null; // 로직 1. Alert 이것을 쓰지 않으면 로그인 사용자 다이얼로그창을 따로 만들정도로 작업이 많아짐.

		switch (type) {
		case 1:
			alert = new Alert(AlertType.WARNING);
			break;
		case 2:
			alert = new Alert(AlertType.CONFIRMATION);
			break;
		case 3:
			alert = new Alert(AlertType.ERROR);
			break;
		case 4:
			alert = new Alert(AlertType.NONE);
			break;
		case 5:
			alert = new Alert(AlertType.INFORMATION);
			break;
		}

		alert.setTitle(title);
		alert.setHeaderText(headerText);
		alert.setContentText(contentText);
		alert.setResizable(false);
		alert.showAndWait();

	}
}
