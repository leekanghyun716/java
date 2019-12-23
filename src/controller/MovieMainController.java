package controller;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.MovieENG;
import model.MovieViewVo;

public class MovieMainController implements Initializable{
	@FXML StackPane MainStack;
	@FXML TableView<MovieViewVo> MovieSelcetTB;
	@FXML DatePicker MovieSelectDP;
	@FXML Button btnInfoMove;
	@FXML Button GoodsTable;
	@FXML ImageView imagePoster;
	String localUrl = ""; // 이미지 파일 경로
	static Image localImage;
	File selectedFile = null;
	private int selectedIndex;
	static ObservableList<MovieViewVo> selectStudent;
	ObservableList<MovieViewVo> data1;
	static String s;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//데이터에 값을 넣어준다
		data1=FXCollections.observableArrayList();
		//유저가 구매란 영화를 보여주기위한 창 띄우기
		GoodsTable.setOnAction(e->{
			Parent parent;
			Stage stage;
				try {
					parent=FXMLLoader.load(getClass().getResource("/view/UserMovieBooks.fxml"));
					stage=new Stage(StageStyle.UTILITY);
					stage.initModality(Modality.WINDOW_MODAL);
					stage.initOwner(GoodsTable.getScene().getWindow());
					Scene scene=new Scene(parent);
					stage.setScene(scene);
					stage.setResizable(false);
					stage.show();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		});
		//유저가 구매할 영화 테이블을 세팅
		MovieTimeTableSetting();
		MovieSelectDP.setOnAction(e->{
			//유저가 구매한 영화 테이블 가져오기
			MovieTimeTableInit();
		});
		//버튼을 누르면 유저가 구매할 영화의 소개가 나온다.
		btnInfoMove.setOnMouseClicked(e->{
			try {
				MovieViewVo eng;
				eng=MovieSelcetTB.getSelectionModel().getSelectedItem();
				System.out.println(eng.getMovieID());
				String count=MovieDAO.getError(eng.getMovieID());
				if(count.equals("35")) {
					alertDisplay(1, "이미 만석입니다!", "만석입니다", "만석입니다");
					return;
				}
				s=MovieSelectDP.getValue().toString();
				StackPane stackPane=(StackPane) MovieSelcetTB.getScene().getRoot();
				AnchorPane anchorPane=FXMLLoader.load(getClass().getResource("/view/MovieInfo.fxml"));
				stackPane.getChildren().add(anchorPane);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		//영화를 테이블을 눌렀을때 테이블 정보 가져오고 이미지 설정
		MovieSelcetTB.setOnMouseReleased(e->{
	    	handlerTableAction();
	    	System.out.println(localImage.toString());
	    	try {
	    	//MoviePoster2.setImage(new Image(localUrl));
	    		imagePoster.setImage(localImage);
	    	}catch(Exception e1) {
	    		e1.printStackTrace();
	    	}
	    }); 
		
	}
	//테이블에 영화 정보를 가져오기 위한 설정
	private void MovieTimeTableSetting() {

		
	      TableColumn colMovieID = new TableColumn("영화아이디");
	      colMovieID.setMaxWidth(300);
	      colMovieID.setPrefWidth(228);
	      colMovieID.setStyle("-fx-alignment: CENTER;");
	      colMovieID.setCellValueFactory(new PropertyValueFactory("MovieID"));

	      TableColumn colMovieTime = new TableColumn("상영시간");
	      colMovieTime.setMaxWidth(200);
	      colMovieTime.setPrefWidth(225);
	      colMovieTime.setStyle("-fx-alignment: CENTER;");
	      colMovieTime.setCellValueFactory(new PropertyValueFactory("MoviewTime"));

	    
	      MovieSelcetTB.setItems(data1); // 6, 함수새로고침 되는 부분
	      MovieSelcetTB.getColumns().addAll(colMovieID,colMovieTime);
		
		
	}
	//테이블에 영화 정보 주기
	private void MovieTimeTableInit() {
		data1.removeAll(data1);
		ArrayList<MovieViewVo> list = null;
		MovieDAO movieDAO = new MovieDAO();
	      MovieViewVo movieViewVo = null;
	      String date=MovieSelectDP.getValue().toString();
	      System.out.println(MovieSelectDP.getValue().toString()+"66666666666666");
	      
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
	//경고창
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
	//테이블에서 설정한 영화 가져오기 
	private void handlerTableAction() {
		selectedIndex = MovieSelcetTB.getSelectionModel().getSelectedIndex();
        selectStudent = MovieSelcetTB.getSelectionModel().getSelectedItems();
        String fileName = selectStudent.get(0).getFileName();
        selectedFile = new File("C:/MoviePoster/"+fileName);
        if(selectedFile != null) {
           try {
			localUrl = selectedFile.toURI().toURL().toString();
			System.out.println(localUrl.toString()+"5555555555555555555555555");
			localImage = new Image(localUrl,false);
			System.out.println(localImage.toString()+"5555555555555555555555555555");
			//imageView.setImage(localImage);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }else {
        	 alertDisplay(1, "경고", "이미지 가져오기 오류", "점검요망");
        }
	}
}
