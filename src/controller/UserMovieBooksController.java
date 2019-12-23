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
		//�����Ϳ� �����͸� �ִ´�.
		data=FXCollections.observableArrayList();
		//������ ��ȭ�� ������ �ִ°� �������� ����
		MovieTimeTableSetting();
		//��ȭ ������ �����´�.
		MovieTimeTableInit();
		btnClose.setOnAction(e->{
			Stage stage=(Stage) btnClose.getScene().getWindow();
			stage.close();
		});
		//�ڽ��� �߸� ������ ���� ȯ���Ҽ� �ֵ��� �Ѵ�.
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
	         alertDisplay(1, "���", "DB �������� ����", "���˿��");
	         return;

	      } else {
	         for (int i = 0; i < list.size(); i++) {
	        	 userBooks = list.get(i);
	            data.add(userBooks);
	         }
	      }
	}
	private void MovieTimeTableSetting() {

		TableColumn colUserID = new TableColumn("�������̵�");
		colUserID.setMaxWidth(200);
		colUserID.setPrefWidth(100);
		colUserID.setStyle("-fx-alignment: CENTER;");
		colUserID.setCellValueFactory(new PropertyValueFactory("userID"));
		
	      TableColumn colMovieID = new TableColumn("��ȭ���̵�");
	      colMovieID.setMaxWidth(300);
	      colMovieID.setPrefWidth(180);
	      colMovieID.setStyle("-fx-alignment: CENTER;");
	      colMovieID.setCellValueFactory(new PropertyValueFactory("MovieID"));

	      
	      TableColumn colMovieTime = new TableColumn("�󿵽ð�");
	      colMovieTime.setMaxWidth(200);
	      colMovieTime.setPrefWidth(80);
	      colMovieTime.setStyle("-fx-alignment: CENTER;");
	      colMovieTime.setCellValueFactory(new PropertyValueFactory("MovieTime"));
	      
	      TableColumn colMovieName = new TableColumn("��ȭ�̸�");
	      colMovieName.setMaxWidth(200);
	      colMovieName.setPrefWidth(80);
	      colMovieName.setStyle("-fx-alignment: CENTER;");
	      colMovieName.setCellValueFactory(new PropertyValueFactory("MovieName"));
	      
	      TableColumn colMoviePrice = new TableColumn("�ѱݾ�");
	      colMoviePrice.setMaxWidth(200);
	      colMoviePrice.setPrefWidth(80);
	      colMoviePrice.setStyle("-fx-alignment: CENTER;");
	      colMoviePrice.setCellValueFactory(new PropertyValueFactory("MoviePrice"));

	    
	      UserBooksTB.setItems(data); // 6, �Լ����ΰ�ħ �Ǵ� �κ�
	      UserBooksTB.getColumns().addAll(colUserID,colMovieID,colMovieTime,colMovieName,colMoviePrice);
		
		
	}
	public void alertDisplay(int type, String title, String headerText, String contentText) {

		Alert alert = null; // ���� 1. Alert �̰��� ���� ������ �α��� ����� ���̾�α�â�� ���� ���������� �۾��� ������.

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
