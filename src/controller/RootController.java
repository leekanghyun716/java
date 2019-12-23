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
		// 0.1 아이디를 쓰라고 하는 문자를 전달한다.
		txtID.setText("아이디를 입력해주세요");
		// 1.1 아이디 텍스트필드 클릭시 안에 있는 텍스트 필드 초기화
		txtID.setOnMouseClicked(e -> handlerBtnIDAction());
		// 1.2 비밀번호 텍스트필드 클릭시 안에 있는 텍스트 필드 초기화
		txtPassword.setOnMouseClicked(e -> handlerBtnPasswordAction());
		// 1.3 회원가입 팝업창 띄우기
		bthHi.setOnAction(e -> handlerBtnHiAction());
		// 1.4로그인
		btnLogin.setOnAction(e->{
			name=txtID.getText();
			handlerBtnLoginAction();
		});
	}

	

	// 1.1 아이디 텍스트필드 클릭시 안에 있는 텍스트 필드 초기화
	private void handlerBtnIDAction() {
		if (txtIDOk == false) {
			txtID.setText("");
			txtIDOk = true;
		}

	}

	// 1.2 비밀번호 텍스트필드 클릭시 안에 있는 텍스트 필드 초기화
	private void handlerBtnPasswordAction() {
		if (txtPasswordOk == false) {
			txtPassword.setText("");
			txtPasswordOk = true;
		}

	}
	//stage를 만들어 회원가입창을 불러온다.
	private void handlerBtnHiAction() {
		Parent hiRoot = null;
		try {
			hiRoot = FXMLLoader.load(getClass().getResource("/view/Hi.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//stage를 만들어 회원가입창을 불러온다.
		Stage stage = new Stage(StageStyle.UTILITY);
		stage.initModality(Modality.WINDOW_MODAL);
		stage.initOwner(btnClose.getScene().getWindow());
		stage.setTitle("회원가입창");
		// =============================================================
		//회원가입에 필요한 ID를 lookup으로 찾기
		TextField txtName = (TextField) hiRoot.lookup("#txtName");
		TextField txtID = (TextField) hiRoot.lookup("#txtID");
		TextField Phone = (TextField) hiRoot.lookup("#Phone");
		Button btnClose = (Button) hiRoot.lookup("#btnClose");
		Button btnHiNew = (Button) hiRoot.lookup("#btnHiNew");
		PasswordField txtPassword = (PasswordField) hiRoot.lookup("#txtPassword");
		DatePicker Date = (DatePicker) hiRoot.lookup("#Date");
		
		// 회원가입창 안에있는 회원가입을 위한 버튼
		btnHiNew.setOnAction(e -> {
			//DatePiker안에있는 값을 불러와서 저장하기 위하여 split으로 저장
			LocalDate data = Date.getValue();
			int date=data.now().getYear();
			String dateData = data.toString();
			System.out.println(dateData);
			String[] array = dateData.split("-");
			int year = Integer.parseInt(array[0]);
			for (String str : array) {
				System.out.println("str=" + str);
			}
			//회원가입창에 있는 정보를 회원관리를 위한 VO에 저장하기 위해서 
			HiNewVO hvo = null;
			try {
				if (txtName == null || txtPassword == null || Date == null || Phone == null) {
					throw new Exception("빈칸이 존재합니다.");
				} else {
					//회원정보를 저장하기위하여 객체생성
					hvo = new HiNewVO(txtName.getText(), txtID.getText(), txtPassword.getText(), year, array[1],
							array[2], Phone.getText(),(date-year));
				}
				movieDAO = new MovieDAO(); // 객체를 부른다.
				try {
					// 데이타베이스에 등록
					int count = movieDAO.getStudentregiste(hvo);
					if (idError == false)
						alertDisplay(1, "회원가입을 축하드림니다.", "회원이 되셨어요!", "축하드려요.");
					idError = false;
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			} catch (Exception e1) {
				alertDisplay(1, "빈칸이 존재합니다.", "빈칸을 수정해주세요", "내용을 다시 확인해주세요.");
				return;
			}
		});
		btnClose.setOnAction(e -> stage.close());
		// =============================================================
		Scene scene = new Scene(hiRoot);
		stage.setScene(scene);
		stage.show();
	}
	//로그인 버튼 관리
	private void handlerBtnLoginAction() {
		MovieDAO movieDAO=new MovieDAO();
		HiNewVO hiNewVO=new HiNewVO();
		hiNewVO.setID(txtID.getText());
		hiNewVO.setPassword(txtPassword.getText());
		
		Parent parent;
		Stage stage;
		//관리하는 사람으로서 로그인을 할경우
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
				//사용자로서 들어갈 경우
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

	// 경고창 디스플레이
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
