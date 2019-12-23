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
	String localUrl = ""; // �̹��� ���� ���
	static Image localImage;
	File selectedFile = null;
	private int selectedIndex;
	static ObservableList<MovieViewVo> selectStudent;
	ObservableList<MovieViewVo> data1;
	static String s;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		//�����Ϳ� ���� �־��ش�
		data1=FXCollections.observableArrayList();
		//������ ���Ŷ� ��ȭ�� �����ֱ����� â ����
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
		//������ ������ ��ȭ ���̺��� ����
		MovieTimeTableSetting();
		MovieSelectDP.setOnAction(e->{
			//������ ������ ��ȭ ���̺� ��������
			MovieTimeTableInit();
		});
		//��ư�� ������ ������ ������ ��ȭ�� �Ұ��� ���´�.
		btnInfoMove.setOnMouseClicked(e->{
			try {
				MovieViewVo eng;
				eng=MovieSelcetTB.getSelectionModel().getSelectedItem();
				System.out.println(eng.getMovieID());
				String count=MovieDAO.getError(eng.getMovieID());
				if(count.equals("35")) {
					alertDisplay(1, "�̹� �����Դϴ�!", "�����Դϴ�", "�����Դϴ�");
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
		//��ȭ�� ���̺��� �������� ���̺� ���� �������� �̹��� ����
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
	//���̺� ��ȭ ������ �������� ���� ����
	private void MovieTimeTableSetting() {

		
	      TableColumn colMovieID = new TableColumn("��ȭ���̵�");
	      colMovieID.setMaxWidth(300);
	      colMovieID.setPrefWidth(228);
	      colMovieID.setStyle("-fx-alignment: CENTER;");
	      colMovieID.setCellValueFactory(new PropertyValueFactory("MovieID"));

	      TableColumn colMovieTime = new TableColumn("�󿵽ð�");
	      colMovieTime.setMaxWidth(200);
	      colMovieTime.setPrefWidth(225);
	      colMovieTime.setStyle("-fx-alignment: CENTER;");
	      colMovieTime.setCellValueFactory(new PropertyValueFactory("MoviewTime"));

	    
	      MovieSelcetTB.setItems(data1); // 6, �Լ����ΰ�ħ �Ǵ� �κ�
	      MovieSelcetTB.getColumns().addAll(colMovieID,colMovieTime);
		
		
	}
	//���̺� ��ȭ ���� �ֱ�
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
	         alertDisplay(1, "���", "DB �������� ����", "���˿��");
	         return;

	      } else {
	         for (int i = 0; i < list.size(); i++) {
	        	 movieViewVo = list.get(i);
	            data1.add(movieViewVo);
	         }
	      }
	}
	//���â
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
	//���̺��� ������ ��ȭ �������� 
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
        	 alertDisplay(1, "���", "�̹��� �������� ����", "���˿��");
        }
	}
}
