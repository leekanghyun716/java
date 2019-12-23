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
		//�����Ϳ� �����͸� �־��ش�
		data1=FXCollections.observableArrayList();
		MovieTimeTableSetting();
		//�ڽ��� �� ��ȭ ���� �� ���ȭ�� �ִ��� Ȯ���Ҽ��ֵ��ϸ� ���ְ� �̻��� ����� ����.
		CarMovieDP.setOnAction(e->{
			//DatePiker�� ���� ��ȭ ���� ��������
			MovieTimeTableInit();
		});
		//����Ʈ ��Ŀ�� �⺻���� �����ϱ� ����
		String[] ss=controller.s.split("-");
		CarMovieDP.setPromptText(controller.s);
		//CarMovieDP.setValue(LocalDate.of(Integer.parseInt(ss[0]), Integer.parseInt(ss[1]), Integer.parseInt(ss[2])));
		//��ȭ ��¥�� �ڽ��� ������ ��ȭ�� �����ϱ� ���� ��ư
		btnOk.setOnAction(e->{
			try {
				if(controller.s.equals(CarMovieDP.getValue().toString())) {
					
				}else {
					alertDisplay(1, "���", "ó���� ������ ��ȭ ��¥�� Ʋ���ϴ�.", "���˿��");
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
		//����ȭ������ ���ư���
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

		
	      TableColumn colMovieID = new TableColumn("��ȭ���̵�");
	      colMovieID.setMaxWidth(300);
	      colMovieID.setPrefWidth(158);
	      colMovieID.setStyle("-fx-alignment: CENTER;");
	      colMovieID.setCellValueFactory(new PropertyValueFactory("MovieID"));

	      TableColumn colMovieTime = new TableColumn("�󿵽ð�");
	      colMovieTime.setMaxWidth(200);
	      colMovieTime.setPrefWidth(225);
	      colMovieTime.setStyle("-fx-alignment: CENTER;");
	      colMovieTime.setCellValueFactory(new PropertyValueFactory("MoviewTime"));

	    
	      CarMovieTB.setItems(data1); // 6, �Լ����ΰ�ħ �Ǵ� �κ�
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
	         alertDisplay(1, "���", "DB �������� ����", "���˿��");
	         return;

	      } else {
	         for (int i = 0; i < list.size(); i++) {
	        	 movieViewVo = list.get(i);
	            data1.add(movieViewVo);
	         }
	      }
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
