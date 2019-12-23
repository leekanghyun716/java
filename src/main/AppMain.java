package main;

import java.time.LocalDate;
import java.util.Date;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AppMain extends Application{

	public static void main(String[] args) {
		
		//System.out.println(array[0]+""+array[1]+""+array[2]);
		launch(args);
		Date date=new Date();
		System.out.println(date.toString());
		LocalDate localDate = null;
		System.out.println(localDate.now()+" "+date.getHours()+"시"+date.getMinutes()+"분");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Parent parent=FXMLLoader.load(getClass().getResource("/view/View.fxml"));
		Scene scene=new Scene(parent);
		primaryStage.setScene(scene);
		primaryStage.setTitle("영화 프로젝트");
		primaryStage.show();
		
	}

}
