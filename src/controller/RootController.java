package controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.HiNewVO;

public class RootController implements Initializable {
	@FXML AnchorPane anco;
	@FXML
	TextField txtID;
	@FXML
	PasswordField txtPassword;
	@FXML
	Button bthHi;
	@FXML
	Button btnLogin;
	@FXML
	Button btnClose;
	private boolean txtIDOk = false;
	private boolean txtPasswordOk = false;
	private MovieDAO movieDAO;
	public static boolean idError = false;
	public static boolean idPasswordError=false;
	public static String name;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		btnClose.setOnAction(e -> Platform.exit());
		// 0.1 ���̵� ����� �ϴ� ���ڸ� �����Ѵ�.
		txtID.setText("���̵� �Է����ּ���");
		// 1.1 ���̵� �ؽ�Ʈ�ʵ� Ŭ���� �ȿ� �ִ� �ؽ�Ʈ �ʵ� �ʱ�ȭ
		txtID.setOnMouseClicked(e -> handlerBtnIDAction());
		// 1.2 ��й�ȣ �ؽ�Ʈ�ʵ� Ŭ���� �ȿ� �ִ� �ؽ�Ʈ �ʵ� �ʱ�ȭ
		txtPassword.setOnMouseClicked(e -> handlerBtnPasswordAction());
		// 1.3 ȸ������ �˾�â ����
		bthHi.setOnAction(e -> handlerBtnHiAction());
		// 1.4�α���
		btnLogin.setOnAction(e->{
			name=txtID.getText();
			handlerBtnLoginAction();
		});
	}

	

	// 1.1 ���̵� �ؽ�Ʈ�ʵ� Ŭ���� �ȿ� �ִ� �ؽ�Ʈ �ʵ� �ʱ�ȭ
	private void handlerBtnIDAction() {
		if (txtIDOk == false) {
			txtID.setText("");
			txtIDOk = true;
		}

	}

	// 1.2 ��й�ȣ �ؽ�Ʈ�ʵ� Ŭ���� �ȿ� �ִ� �ؽ�Ʈ �ʵ� �ʱ�ȭ
	private void handlerBtnPasswordAction() {
		if (txtPasswordOk == false) {
			txtPassword.setText("");
			txtPasswordOk = true;
		}

	}
	//stage�� ����� ȸ������â�� �ҷ��´�.
	private void handlerBtnHiAction() {
		Parent hiRoot = null;
		try {
			hiRoot = FXMLLoader.load(getClass().getResource("/view/Hi.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//stage�� ����� ȸ������â�� �ҷ��´�.
		Stage stage = new Stage(StageStyle.UTILITY);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(btnClose.getScene().getWindow());
		stage.setTitle("ȸ������â");
		// =============================================================
		//ȸ�����Կ� �ʿ��� ID�� lookup���� ã��
		TextField txtName = (TextField) hiRoot.lookup("#txtName");
		TextField txtID = (TextField) hiRoot.lookup("#txtID");
		TextField Phone = (TextField) hiRoot.lookup("#Phone");
		Button btnClose = (Button) hiRoot.lookup("#btnClose");
		Button btnHiNew = (Button) hiRoot.lookup("#btnHiNew");
		PasswordField txtPassword = (PasswordField) hiRoot.lookup("#txtPassword");
		DatePicker Date = (DatePicker) hiRoot.lookup("#Date");
		
		// ȸ������â �ȿ��ִ� ȸ�������� ���� ��ư
		btnHiNew.setOnAction(e -> {
			//DatePiker�ȿ��ִ� ���� �ҷ��ͼ� �����ϱ� ���Ͽ� split���� ����
			LocalDate data = Date.getValue();
			int date=data.now().getYear();
			String dateData = data.toString();
			System.out.println(dateData);
			String[] array = dateData.split("-");
			int year = Integer.parseInt(array[0]);
			for (String str : array) {
				System.out.println("str=" + str);
			}
			//ȸ������â�� �ִ� ������ ȸ�������� ���� VO�� �����ϱ� ���ؼ� 
			HiNewVO hvo = null;
			try {
				if (txtName == null || txtPassword == null || Date == null || Phone == null) {
					throw new Exception("��ĭ�� �����մϴ�.");
				} else {
					//ȸ�������� �����ϱ����Ͽ� ��ü����
					hvo = new HiNewVO(txtName.getText(), txtID.getText(), txtPassword.getText(), year, array[1],
							array[2], Phone.getText(),(date-year));
				}
				movieDAO = new MovieDAO(); // ��ü�� �θ���.
				try {
					// ����Ÿ���̽��� ���
					int count = movieDAO.getStudentregiste(hvo);
					if (idError == false)
						alertDisplay(1, "ȸ�������� ���ϵ帲�ϴ�.", "ȸ���� �Ǽ̾��!", "���ϵ����.");
					idError = false;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} catch (Exception e1) {
				alertDisplay(1, "��ĭ�� �����մϴ�.", "��ĭ�� �������ּ���", "������ �ٽ� Ȯ�����ּ���.");
				return;
			}
		});
		btnClose.setOnAction(e -> stage.close());
		// =============================================================
		Scene scene = new Scene(hiRoot);
		stage.setScene(scene);
		stage.show();
	}
	//�α��� ��ư ����
	private void handlerBtnLoginAction() {
		MovieDAO movieDAO=new MovieDAO();
		HiNewVO hiNewVO=new HiNewVO();
		hiNewVO.setID(txtID.getText());
		hiNewVO.setPassword(txtPassword.getText());
		
		Parent parent;
		Stage stage;
		//�����ϴ� ������μ� �α����� �Ұ��
		if(txtID.getText().equals("admin")&&txtPassword.getText().equals("1234")) {
			try {
				parent=FXMLLoader.load(getClass().getResource("/view/Management.fxml"));
				stage=new Stage(StageStyle.UTILITY);
				stage.initModality(Modality.WINDOW_MODAL);
				stage.initOwner(btnClose.getScene().getWindow());
				Scene scene=new Scene(parent);
				stage.setScene(scene);
				Stage stage2=(Stage) txtID.getScene().getWindow();
				stage2.close();
				stage.setResizable(false);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		movieDAO.getHiNewInfoID(hiNewVO);
		movieDAO.getHiNewInfoPassword(hiNewVO);
		
		if(idPasswordError==false) {
			try {
				//����ڷμ� �� ���
				parent=FXMLLoader.load(getClass().getResource("/view/MainStack.fxml"));
				stage=new Stage(StageStyle.UTILITY);
				stage.initModality(Modality.WINDOW_MODAL);
				stage.initOwner(btnClose.getScene().getWindow());
				Scene scene=new Scene(parent);
				stage.setScene(scene);
				Stage stage2=(Stage) txtID.getScene().getWindow();
				stage2.close();
				stage.setResizable(false);
				stage.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		idPasswordError=false;
	}

	// ���â ���÷���
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
