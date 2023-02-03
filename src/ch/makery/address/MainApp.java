package ch.makery.address;

import java.io.IOException;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AdressApp");
		
		initRootLayout();
		
		showPersonOverview();
		
	}

	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("View/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
		
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			
	}	catch (IOException e) {
			e.printStackTrace();
		}
			
	}
	
	public void showPersonOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonOverview.fmxl"));
			AnchorPane PersonOverview = (AnchorPane) loader.load();
			rootLayout.setCenter(PersonOverview);
	}   catch (IOException e) {
			e.printStackTrace();
	}
}	
	
		public Stage getPrimaryStage() {
			return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
