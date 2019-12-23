package controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.ResourceBundle;

import javax.security.auth.login.FailedLoginException;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;
import model.HiNewVO;
import model.MovieUpVO;
import model.MovieViewVo;
import model.UserBooks;
import model.UserChartVO;

public class ManagementController implements Initializable {
	@FXML
	Button btnDelete;
	@FXML
	Button btnCloseChart;
	@FXML
	Button btnpieChart;
	@FXML
	Button ResetBtn;
	@FXML
	PieChart pieChart;
	@FXML
	DatePicker datepick;
	@FXML
	AnchorPane anchoPaneMovie;
	@FXML
	AnchorPane anchoPaneUser;
	@FXML
	AnchorPane anchoPaneMovieView;
	@FXML
	AnchorPane anchoPaneUserView;
	@FXML
	TableView tableviewMovie;
	@FXML
	TableView TableUserBooks;
	@FXML
	ImageView moviePoster5;
	@FXML
	Tab tab1;
	@FXML
	Button btnSu;
	@FXML
	Button btnDeleteUser;
	@FXML
	TableView txt1;
	@FXML
	Button btnUpdate;
	@FXML
	TableView UserTB;
	ObservableList<MovieUpVO> data;
	ObservableList<HiNewVO> data2;
	ObservableList<MovieViewVo> data3;
	ObservableList<UserBooks> data4;
	ObservableList<MovieViewVo> data1;
	String selectFileName = ""; // �̹��� ���ϸ�
	String localUrl = ""; // �̹��� ���� ���
	Image localImage;
	MovieDAO movieDAO;
	int no; // ������ ���̺��� ������ �л��� ��ȣ ����
	File selectedFile = null;
	private int selectedIndex;
	private ObservableList<MovieViewVo> selectStudent;
	private ObservableList<HiNewVO> selectHiNew;
	// �̹��� ó��
	// �̹��� ������ ������ �Ű������� ���� ��ü ����
	private File dirSave = new File("C:/MoviePoster");
	// �̹��� �ҷ��� ������ ������ ���� ��ü ����
	private File file = null;
	ImageView imageView;
	String fileName2;
	String btnTime;
	int radioAge = 0;
	private boolean editDelete = false;
	// ��ư�� ���� ���ִ� ����
	boolean btn3Color = false;
	boolean btn5Color = false;
	boolean btn7Color = false;
	boolean btn9Color = false;
	// ������ư�� �������ִ� ��
	boolean radioAge1 = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// �����Ϳ� ���� �־��ش�.-�ȳ־��ָ� Null������ ���.
		data1 = FXCollections.observableArrayList();
		data2 = FXCollections.observableArrayList();
		data3 = FXCollections.observableArrayList();
		data4 = FXCollections.observableArrayList();
		// ������ ������ ���̺��� ����
		userMovieTimeTableSetting();

		// ������ ������ ���̺��� ��������
		userMovieTimeTableInit();
		// ���̺� ������ ì���
		tableViewSetting();
		// ���̺� �����ٰ� ����
		tableViewInit();
		//DatePiker���ִ�  ��¥�� ���ýÿ� ���� �̺�Ʈ�� ���� ���̺� ����
		MovieTimeTableSetting();
		//���� ȸ�������� �� ������ ����
		UserTableSetting();
		//���� ȸ�������� �� ������ ���̺� ��������
		UserTableInit();
		//����Ʈ ��Ŀ�� ����Ͽ� ������ ��¥�� ��ϵ� ��ȭ�� �������� 
		datepick.setOnAction(e -> {
			MovieTimeTableInit();
			System.out.println("dhdhdhdhdhddh");
		});
		//���õ� ���̺� ������ �����
		btnDeleteUser.setOnAction(e -> {
			selectedIndex = UserTB.getSelectionModel().getSelectedIndex();
			selectHiNew = UserTB.getSelectionModel().getSelectedItems();
			try {
				System.out.println(selectHiNew.get(0).getClass().toString());

				MovieDAO.deleteUser(selectHiNew.get(0).getID());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			data2.removeAll(data2);
			UserTableInit();
		});
		//���� ���� ���� ���ΰ�ħ
		ResetBtn.setOnAction(e -> {
			data4.removeAll(data4);
			userMovieTimeTableInit();
		});
		// totalList();
		// ===========================================================================================
		// 1.��ȭ ������̺��� ������!
		Button btnMovieOk = (Button) anchoPaneMovie.lookup("#btnMovieOk");
		Button btnMovieSelect = (Button) anchoPaneMovie.lookup("#btnMovieSelect");
		Button MovieSu = (Button) anchoPaneMovie.lookup("#MovieSu");
		Button btn3 = (Button) anchoPaneMovie.lookup("#btn3");
		Button btn5 = (Button) anchoPaneMovie.lookup("#btn5");
		Button btn7 = (Button) anchoPaneMovie.lookup("#btn7");
		Button btn9 = (Button) anchoPaneMovie.lookup("#btn9");
		TextArea Jul = (TextArea) anchoPaneMovie.lookup("#Jul");
		TextField txtMovieName = (TextField) anchoPaneMovie.lookup("#txtMovieName");
		ImageView MoviePosterView1 = (ImageView) anchoPaneMovie.lookup("#MoviePosterView1");
		TableView txt = (TableView) anchoPaneMovie.lookup("#txt");
		DatePicker MovieDate = (DatePicker) anchoPaneMovie.lookup("#MovieDate");
		RadioButton MovieAgeLimit = (RadioButton) anchoPaneMovie.lookup("#MovieAgeLimit");

		// =========================================================================================
		//�����ϱ�
		btnSu.setOnAction(e -> {
			//��ư�ʱ�ȭ
			btn5.setDisable(false);
			btn7.setDisable(false);
			btn9.setDisable(false);
			btn3.setDisable(false);
			btn3.setStyle(" -fx-background-color: white");
			btn5.setStyle(" -fx-background-color: white");
			btn7.setStyle(" -fx-background-color: white");
			btn9.setStyle(" -fx-background-color: white");

			btn3.setStyle(" -fx-background-color: white");
			btn5.setStyle(" -fx-background-color: white");
			btn7.setStyle(" -fx-background-color: white");
			btn9.setStyle(" -fx-background-color: white");
			boolean btn3Color = false;
			boolean btn5Color = false;
			boolean btn7Color = false;
			boolean btn9Color = false;
			//��ϵ� ��ȭ ���� ��������
			selectedIndex = tableviewMovie.getSelectionModel().getSelectedIndex();
			selectStudent = tableviewMovie.getSelectionModel().getSelectedItems();

			String getid = selectStudent.get(0).getMovieID();
			System.out.println(getid);
			ArrayList<MovieUpVO> arrayList = new ArrayList<MovieUpVO>();
			arrayList = movieDAO.getSujungTime(getid);
			Jul.setText(arrayList.get(0).getJulgary());
			txtMovieName.setText(arrayList.get(0).getMovieName());

			String[] movieDate = arrayList.get(0).getMovieID().split("-");
			System.out.println(arrayList.get(0).getMovieID() + "999999999999999999999");

			LocalDate localDate = null;
			System.out.println(movieDate[0].replaceAll("[^0-9]", "") + "ssssssssssssssssssss");
			MovieDate.setValue(localDate.of(Integer.parseInt(movieDate[0].replaceAll("[^0-9]", "")),
					Integer.parseInt(movieDate[1]), Integer.parseInt(movieDate[2])));

			MoviePosterView1.setImage(localImage);
			if (arrayList.get(0).getViewAge() == 19)
				MovieAgeLimit.setSelected(true);
			else {
				MovieAgeLimit.setSelected(false);
			}
			//������ ��ư�� �ش��ϴ� �ð��� ����
			String btnTime = arrayList.get(0).getMoviewTime();
			switch (btnTime) {
			case "3:30":
				btn3.fire();
				break;
			case "5:20":
				btn5.fire();
				break;
			case "7:40":
				btn7.fire();
				break;
			case "9:40":
				btn9.fire();
				break;
			}

		});
		//�⺻�̹��� �Ϳ��� ��ī
		localUrl = "/images/cua.jpg";
		localImage = new Image(localUrl, false);
		MoviePosterView1.setImage(localImage);
		System.out.println(localImage.toString() + "888888888888888888888888");
		//������ư�� �̺�Ʈ
		MovieAgeLimit.setOnAction(e -> {
			if (radioAge1 == false) {
				radioAge = Integer.parseInt(MovieAgeLimit.getText());
				radioAge1 = true;
			} else {
				radioAge = 0;
				radioAge1 = false;
			}
		});
		//���̾˷α׷� �̹��� ����
		btnMovieSelect.setOnAction(e -> {
			MoviePosterView1.setImage(fileChoose());
		});
		//��ư �����̺�Ʈ
		btn3.setOnAction(e -> {
			btnTime = null;
			btnTime = btn3.getText();
			if (btn3Color == false) {
				btn3.setStyle(" -fx-background-color: red");
				btn3Color = true;
			} else {
				btn3.setStyle(" -fx-background-color: white");
				btn3Color = false;
			}
			//��ư����
			if (btn3Color == true) {
				btn5.setDisable(true);
				btn7.setDisable(true);
				btn9.setDisable(true);
			} else {
				btn5.setDisable(false);
				btn7.setDisable(false);
				btn9.setDisable(false);
			}
		});
		//��ư �����̺�Ʈ
		btn5.setOnAction(e -> {
			btnTime = null;
			btnTime = btn5.getText();
			if (btn5Color == false) {
				btn5.setStyle(" -fx-background-color: red");
				btn5Color = true;
			} else {
				btn5.setStyle(" -fx-background-color: white");
				btn5Color = false;
			}
			if (btn5Color == true) {
				btn3.setDisable(true);
				btn7.setDisable(true);
				btn9.setDisable(true);
			} else {
				btn3.setDisable(false);
				btn7.setDisable(false);
				btn9.setDisable(false);
			}
		});
		//��ư �����̺�Ʈ
		btn7.setOnAction(e -> {
			btnTime = null;
			btnTime = btn7.getText();
			if (btn7Color == false) {
				btn7.setStyle(" -fx-background-color: red");
				btn7Color = true;
			} else {
				btn7.setStyle(" -fx-background-color: white");
				btn7Color = false;
			}
			if (btn7Color == true) {
				btn3.setDisable(true);
				btn5.setDisable(true);
				btn9.setDisable(true);
			} else {
				btn5.setDisable(false);
				btn3.setDisable(false);
				btn9.setDisable(false);
			}
		});
		//��ư �����̺�Ʈ
		btn9.setOnAction(e -> {
			btnTime = null;
			btnTime = btn9.getText();
			if (btn9Color == false) {
				btn9.setStyle(" -fx-background-color: red");
				btn9Color = true;
			} else {
				btn9.setStyle(" -fx-background-color: white");
				btn9Color = false;
			}
			if (btn9Color == true) {
				btn5.setDisable(true);
				btn7.setDisable(true);
				btn3.setDisable(true);
			} else {
				btn5.setDisable(false);
				btn7.setDisable(false);
				btn3.setDisable(false);
			}
		});
		// ���̺� ���
		btnMovieOk.setOnAction(e -> {
			try { // ������ �߻��ɶ��� ���

				File dirMake = new File(dirSave.getAbsolutePath());

				// �̹��� ���� ���� ����
				if (!dirMake.exists()) {
					dirMake.mkdir();
				}
				MoviePosterView1.getImage().toString();
				selectedFile = new File("C:/MoviePoster/" + fileName2);

				// �̹��� ���� ����
				String fileName = imageSave(selectedFile);
				System.out.println("fileName = " + fileName); // �����

				if (txtMovieName.getText().equals("") || Jul.getText().equals("")) {
					throw new Exception("�ȳ�");
				} else {
					String MovieId = txtMovieName.getText() + MovieDate.getValue().toString();
					MovieUpVO svo = new MovieUpVO(MovieId, txtMovieName.getText(), btnTime, Jul.getText(), fileName2,
							radioAge);
					String movie = fileName2;
					MovieViewVo mvv = new MovieViewVo(MovieId, txtMovieName.getText(), btnTime, 0, 0, movie);
					// (MovieID,MovieName,MoviewTime,julgary,filedata,viewAge)
					int count1 = 0;
					// ���̺� �信 ������ ����
					if (editDelete == true) {
						data.remove(0);
						data.add(0, svo);
						editDelete = false;
					} else {
						movieDAO = new MovieDAO(); // ��ü�� �θ���.
						int count = MovieDAO.getMovieregiste(svo);
						count1 = MovieDAO.getMovieVieAction(mvv);
						if (count1 == 0) {
							alertDisplay(1, "��� ����", "��ȭ �󿵳�¥�� ��Ĩ�ϴ�.", "���̺� ��ȭ�� ��Ͻ���");
						}
						// �����ͺ��̽� ���̺� ���ڵ� ���� �־���� ����
					}
					if (count1 != 0)
						alertDisplay(1, "��� ����", "���̺� ��� �Ϸ�", "���̺� ��ȭ�� ����մϴ�.");
				}
			} catch (SQLException e2) {
				alertDisplay(1, "��� ����", "������ �����", e2.toString());
				e2.printStackTrace();
				return;
			} catch (Exception e2) {
				alertDisplay(1, "��� ����", "������ �����", e2.toString());
				e2.printStackTrace();
				return;
			}
			data1.removeAll(data1);
			data3.removeAll(data3);
			tableViewInit();
		});
		// =========================================================================================
		TableView movieViewTable = (TableView) anchoPaneMovieView.lookup("#movieViewTable");
		ImageView MoviePoster2 = (ImageView) anchoPaneUserView.lookup("#MoviePoster2");
		Button btnSU = (Button) anchoPaneUserView.lookup("#btnSU");

		// ������ư
		btnDelete.setOnAction(e -> {
			selectedIndex = tableviewMovie.getSelectionModel().getSelectedIndex();
			selectStudent = tableviewMovie.getSelectionModel().getSelectedItems();

			MovieDAO.deleteSujung(selectStudent.get(0).getMovieID());
			MovieDAO.deleteSujung1(selectStudent.get(0).getMovieID());

			data1.removeAll(data1);
			tableViewInit();
		});
		//��ϵ� ��ȭ ���̺� Ŭ���� ��ȭ������ ����
		tableviewMovie.setOnMouseReleased(e -> {
			handlerTableAction();
			System.out.println(localImage.toString());
			try {
				// MoviePoster2.setImage(new Image(localUrl));
				moviePoster5.setImage(localImage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		//��ȭ�� ������ �������
		btnUpdate.setOnAction(e -> {
			//���̺��� ������ �� �ҷ�����
			selectedIndex = tableviewMovie.getSelectionModel().getSelectedIndex();
			selectStudent = tableviewMovie.getSelectionModel().getSelectedItems();

			String MovieId = txtMovieName.getText() + MovieDate.getValue().toString();
			MovieUpVO svo = new MovieUpVO(MovieId, txtMovieName.getText(), btnTime, Jul.getText(), fileName2, radioAge);
			System.out.println(Jul.getText());
			MovieDAO.getMovieSuJung(svo, selectStudent.get(0).getMovieID(), selectStudent.get(0).getFileName());
			MovieDAO.getMovieSuJung1(svo, selectStudent.get(0).getMovieID(), selectStudent.get(0).getFileName());

			data1.removeAll(data1);
			tableViewInit();
		});
		//������Ʈ����
		btnpieChart.setOnAction(e -> {
			getPieChart();
		});
		// ==================================================================================================
	}
	//���̺��� ������ ��ȭ�� ������
	private void handlerTableAction() {
		selectedIndex = tableviewMovie.getSelectionModel().getSelectedIndex();
		selectStudent = tableviewMovie.getSelectionModel().getSelectedItems();
		String fileName = selectStudent.get(0).getFileName();
		selectedFile = new File("C:/MoviePoster/" + fileName);
		if (selectedFile != null) {
			try {
				localUrl = selectedFile.toURI().toURL().toString();
				System.out.println(localUrl.toString() + "5555555555555555555555555");
				localImage = new Image(localUrl, false);
				System.out.println(localImage.toString() + "5555555555555555555555555555");
				// imageView.setImage(localImage);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			imageViewInit();
		}
	}

	// ���̺� �����ٰ� ����
	private void tableViewInit() {
		ArrayList<MovieViewVo> list = null;
		MovieDAO movieDAO = new MovieDAO();
		MovieViewVo movieViewVo = null;
		list = MovieDAO.getStudentTotal();

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

	// ���̺� ������ ì���
	private void tableViewSetting() {
		TableColumn colMovieName = new TableColumn("��ȭ�̸�");
		colMovieName.setMaxWidth(200);
		colMovieName.setStyle("-fx-alignment: CENTER;");
		colMovieName.setCellValueFactory(new PropertyValueFactory("MovieName"));

		TableColumn colMovieID = new TableColumn("��ȭ���̵�");
		colMovieID.setMaxWidth(300);
		colMovieID.setStyle("-fx-alignment: CENTER;");
		colMovieID.setCellValueFactory(new PropertyValueFactory("MovieID"));

		TableColumn colMovieTime = new TableColumn("�󿵽ð�");
		colMovieTime.setMaxWidth(200);
		colMovieTime.setStyle("-fx-alignment: CENTER;");
		colMovieTime.setCellValueFactory(new PropertyValueFactory("MoviewTime"));

		TableColumn colMovieTotalPrice = new TableColumn("�Ѹ����");
		colMovieTotalPrice.setMaxWidth(200);
		colMovieTotalPrice.setStyle("-fx-alignment: CENTER;");
		colMovieTotalPrice.setCellValueFactory(new PropertyValueFactory("MovieTotalPrice"));

		TableColumn colTotalView = new TableColumn("���������");
		colTotalView.setMaxWidth(200);
		colTotalView.setStyle("-fx-alignment: CENTER;");
		colTotalView.setCellValueFactory(new PropertyValueFactory("MovieTotalView"));

		TableColumn colPosterView = new TableColumn("��ȭ ������");
		colPosterView.setMaxWidth(200);
		colPosterView.setStyle("-fx-alignment: CENTER;");
		colPosterView.setCellValueFactory(new PropertyValueFactory("fileName"));

		tableviewMovie.setItems(data1); // 6, �Լ����ΰ�ħ �Ǵ� �κ�
		tableviewMovie.getColumns().addAll(colMovieID, colMovieName, colMovieTime, colMovieTotalPrice, colTotalView,
				colPosterView);

	}
	//��ȭ�ð��� ���̺� ��������
	private void MovieTimeTableInit() {
		data3.removeAll(data3);
		ArrayList<MovieViewVo> list = null;
		MovieDAO movieDAO = new MovieDAO();
		MovieViewVo movieViewVo = null;
		String date = datepick.getValue().toString();
		System.out.println(datepick.getValue().toString() + "66666666666666");

		list = MovieDAO.getMovieTime(date);

		System.out.println(list.get(0).getMovieID());

		if (list == null) {
			alertDisplay(1, "���", "DB �������� ����", "���˿��");
			return;

		} else {
			for (int i = 0; i < list.size(); i++) {
				movieViewVo = list.get(i);
				data3.add(movieViewVo);
			}
		}
	}
	//��ȭ �ð��� ���̺� �����
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

		txt1.setItems(data3); // 6, �Լ����ΰ�ħ �Ǵ� �κ�
		txt1.getColumns().addAll(colMovieID, colMovieTime);

	}
	//���̾�α� �̹��� ã�ƿ���
	public Image fileChoose() {
		// ���� ���� â
		FileChooser fc = new FileChooser();
		fc.setTitle("�̹��� ����");
		fc.setInitialDirectory(new File("C:/MoviePoster")); // default ���丮 ����
		// ������ ���� ���� ����
		// Ȯ���� ����
		ExtensionFilter imgType = new ExtensionFilter("image file", "*.jpg", "*.gif", "*.png");
//      fc.getExtensionFilters().add(imgType);
		ExtensionFilter txtType = new ExtensionFilter("text file", "*.txt", "*.doc");
		fc.getExtensionFilters().addAll(imgType, txtType);

		File selectedFile = fc.showOpenDialog(null); // showOpenDialog�� â�� ���µ� ��� ��ġ�� ������ ���ڸ� �ް�
														// �׸��� ������ ������ ��ΰ��� ��ȯ�Ѵ�.
		System.out.println(selectedFile + "   selectedFile"); // ������ ��ΰ� ��µȴ�.
		fileName2 = selectedFile.getName();
		System.out.println(fileName2.toString());
		// ������ InputStream���� �о��
		try {
			// ���� �о����
			FileInputStream fis = new FileInputStream(selectedFile);
			BufferedInputStream bis = new BufferedInputStream(fis);
			// �̹��� �����ϱ�
			localImage = new Image(bis);
			// �̹��� ����
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return localImage;

	}
	//ã�� �̹����� �����ο� ����
	public String imageSave(File file1) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		int data = -1;
		String fileName = null;
		try {
			// �̹��� ���ϸ� ����
			fileName = "Movies" + System.currentTimeMillis() + "_" + file1.getName();
			System.out.println(file1.getName() + "==================================");
			System.out.println(fileName + "////////////////////");
			bis = new BufferedInputStream(new FileInputStream(file1));
			bos = new BufferedOutputStream(new FileOutputStream(dirSave.getAbsolutePath() + "\\" + fileName));
			System.out.println(dirSave.getAbsolutePath().toString() + "******************");
			// ������ �̹��� ���� InputStream�� �������� �̸����� ���� -1
			while ((data = bis.read()) != -1) {
				bos.write(data);
				bos.flush();
			}
		} catch (Exception e) {
			e.getMessage();
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (bis != null) {
					bis.close();
				}
			} catch (IOException e) {
				e.getMessage();
			}
		}
		return fileName;

	}
	//�̹����� �ҷ�����
	public void imageViewInit() {
		localUrl = "/images/profile.png";
		localImage = new Image(localUrl, false);
		imageView.setImage(localImage);

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
	//������ ������ ���� ���̺� ��������
	private void UserTableSetting() {

		TableColumn colUserID = new TableColumn("���� ���̵�");
		colUserID.setMaxWidth(200);
		colUserID.setPrefWidth(225);
		colUserID.setStyle("-fx-alignment: CENTER;");
		colUserID.setCellValueFactory(new PropertyValueFactory("ID"));

		TableColumn colUserPassword = new TableColumn("���� �н�����");
		colUserPassword.setMaxWidth(200);
		colUserPassword.setPrefWidth(100);
		colUserPassword.setStyle("-fx-alignment: CENTER;");
		colUserPassword.setCellValueFactory(new PropertyValueFactory("Password"));

		TableColumn colUserName = new TableColumn("���� �̸�");
		colUserName.setMaxWidth(300);
		colUserName.setPrefWidth(80);
		colUserName.setStyle("-fx-alignment: CENTER;");
		colUserName.setCellValueFactory(new PropertyValueFactory("name"));

		TableColumn colUserYear = new TableColumn("����");
		colUserYear.setMaxWidth(300);
		colUserYear.setPrefWidth(80);
		colUserYear.setStyle("-fx-alignment: CENTER;");
		colUserYear.setCellValueFactory(new PropertyValueFactory("Year"));
		TableColumn colUserPhone = new TableColumn("��ȭ��ȣ");
		colUserPhone.setMaxWidth(300);
		colUserPhone.setPrefWidth(80);
		colUserPhone.setStyle("-fx-alignment: CENTER;");
		colUserPhone.setCellValueFactory(new PropertyValueFactory("Phone"));

		UserTB.setItems(data2); // 6, �Լ����ΰ�ħ �Ǵ� �κ�
		UserTB.getColumns().addAll(colUserID, colUserPassword, colUserName, colUserYear, colUserPhone);

	}
	//���� ���̺� ��������
	private void UserTableInit() {
		data3.removeAll(data3);
		ArrayList<HiNewVO> list = null;
		MovieDAO movieDAO = new MovieDAO();
		HiNewVO hiNewVO = null;

		list = MovieDAO.getUser();

		if (list == null) {
			alertDisplay(1, "���", "DB �������� ����", "���˿��");
			return;

		} else {
			for (int i = 0; i < list.size(); i++) {
				hiNewVO = list.get(i);
				data2.add(hiNewVO);
			}
		}
	}
	//������ ������ ��ȭ ��������
	private void userMovieTimeTableInit() {
		data4.removeAll(data4);
		ArrayList<UserBooks> list = null;
		MovieDAO movieDAO = new MovieDAO();
		UserBooks userBooks = null;

		list = MovieDAO.getUserGoodsTableTotal();

		if (list == null) {
			alertDisplay(1, "���", "DB �������� ����", "���˿��");
			return;

		} else {
			for (int i = 0; i < list.size(); i++) {
				userBooks = list.get(i);
				data4.add(userBooks);
			}
		}
	}
	//������ ������ ��ȭ �����ϱ�
	private void userMovieTimeTableSetting() {
		data4 = FXCollections.observableArrayList();

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

		TableUserBooks.setItems(data4); // 6, �Լ����ΰ�ħ �Ǵ� �κ�
		TableUserBooks.getColumns().addAll(colUserID, colMovieID, colMovieTime, colMovieName, colMoviePrice);

	}
	//������Ʈ ��������
	private void getPieChart() {
		try {
			// �ι� Ŭ���� �Ǿ�����
			ArrayList<UserChartVO> list;
			list = MovieDAO.getChartElement();

			// �׷��� �׸���
			pieChart.setData(FXCollections.observableArrayList(
					new PieChart.Data(list.get(0).getMovieName() + "-" + Integer.toString(list.get(0).getMoviePrice()),
							(double) list.get(0).getMoviePrice()),
					new PieChart.Data(list.get(1).getMovieName() + "-" + Integer.toString(list.get(1).getMoviePrice()),
							(double) list.get(1).getMoviePrice()),
					new PieChart.Data(list.get(2).getMovieName() + "-" + Integer.toString(list.get(2).getMoviePrice()),
							(double) list.get(2).getMoviePrice())));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
