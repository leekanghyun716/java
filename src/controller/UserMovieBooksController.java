package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.HiNewVO;
import model.MovieUpVO;
import model.MovieViewVo;
import model.UserBooks;

public class UserMovieBooksController implements Initializable{
	@FXML TableView UserBooksTB;
	@FXML Button btnClose;
	@FXML Button btnDelete;
	ObservableList<UserBooks> data;
	private ObservableList<UserBooks> selectHiNew;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//데이터에 데이터를 넣는다.
		data=FXCollections.observableArrayList();
		//예매한 영화는 무었이 있는가 보기위한 설정
		MovieTimeTableSetting();
		//영화 내용을 가져온다.
		MovieTimeTableInit();
		btnClose.setOnAction(e->{
			Stage stage=(Stage) btnClose.getScene().getWindow();
			stage.close();
		});
		//자신이 잘못 예매한 것을 환불할수 있도록 한다.
		btnDelete.setOnAction(e->{
			selectHiNew = UserBooksTB.getSelectionModel().getSelectedItems();
			try {
			System.out.println(selectHiNew.get(0).getMovieID());

			MovieDAO.deleteUserGoods(selectHiNew.get(0).getMovieID());
			}catch(Exception e1) {
				e1.printStackTrace();
			}
			data.removeAll(data);
			MovieTimeTableInit();
		});
	}
	private void MovieTimeTableInit() {
		data.removeAll(data);
		ArrayList<UserBooks> list = null;
		MovieDAO movieDAO = new MovieDAO();
		UserBooks userBooks = null;
	      
	      list = MovieDAO.getUserGoodsTable(RootController.name);
	      
	      if (list == null) {
	         alertDisplay(1, "경고", "DB 가져오기 오류", "점검요망");
	         return;

	      } else {
	         for (int i = 0; i < list.size(); i++) {
	        	 userBooks = list.get(i);
	            data.add(userBooks);
	         }
	      }
	}
	private void MovieTimeTableSetting() {

		TableColumn colUserID = new TableColumn("유저아이디");
		colUserID.setMaxWidth(200);
		colUserID.setPrefWidth(100);
		colUserID.setStyle("-fx-alignment: CENTER;");
		colUserID.setCellValueFactory(new PropertyValueFactory("userID"));
		
	      TableColumn colMovieID = new TableColumn("영화아이디");
	      colMovieID.setMaxWidth(300);
	      colMovieID.setPrefWidth(180);
	      colMovieID.setStyle("-fx-alignment: CENTER;");
	      colMovieID.setCellValueFactory(new PropertyValueFactory("MovieID"));

	      
	      TableColumn colMovieTime = new TableColumn("상영시간");
	      colMovieTime.setMaxWidth(200);
	      colMovieTime.setPrefWidth(80);
	      colMovieTime.setStyle("-fx-alignment: CENTER;");
	      colMovieTime.setCellValueFactory(new PropertyValueFactory("MovieTime"));
	      
	      TableColumn colMovieName = new TableColumn("영화이름");
	      colMovieName.setMaxWidth(200);
	      colMovieName.setPrefWidth(80);
	      colMovieName.setStyle("-fx-alignment: CENTER;");
	      colMovieName.setCellValueFactory(new PropertyValueFactory("MovieName"));
	      
	      TableColumn colMoviePrice = new TableColumn("총금액");
	      colMoviePrice.setMaxWidth(200);
	      colMoviePrice.setPrefWidth(80);
	      colMoviePrice.setStyle("-fx-alignment: CENTER;");
	      colMoviePrice.setCellValueFactory(new PropertyValueFactory("MoviePrice"));

	    
	      UserBooksTB.setItems(data); // 6, 함수새로고침 되는 부분
	      UserBooksTB.getColumns().addAll(colUserID,colMovieID,colMovieTime,colMovieName,colMoviePrice);
		
		
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
