package ch.makery.address;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.controlsfx.dialog.Dialogs;

import ch.makery.address.model.Person;
import ch.makery.address.model.PersonListWrapper;
import ch.makery.address.view.BirthdayStatisticsController;
import ch.makery.address.view.PersonEditDialogController;
import ch.makery.address.view.PersonOverviewController;
import ch.makery.address.view.RootLayoutController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;


public class MainApp extends Application {

	private Stage primaryStage;
	private BorderPane rootLayout;
	
	
	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("AdressApp");
		this.primaryStage.getIcons().add(new Image("file:resources/images/addres_book_image.png"));
		
		initRootLayout();
		
		showPersonOverview();
		
	}

	public void initRootLayout() {
		
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
			rootLayout = (BorderPane) loader.load();
			Scene scene = new Scene(rootLayout);
			primaryStage.setScene(scene);
			primaryStage.show();
			RootLayoutController controller = loader.getController();
			controller.setMainApp(this);
			primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		File file = getPersonFilePath();
		if (file != null) {
			setPersonFilePath(file);
		}
	}
	
	public void showPersonOverview() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("view/PersonOverview.fxml"));
			AnchorPane personOverview = (AnchorPane) loader.load();
			rootLayout.setCenter(personOverview);
			PersonOverviewController controller = loader.getController();
			controller.setMainApp(this);
			
	}   catch (IOException e) {
			e.printStackTrace();
	}
}	
	
		public Stage getPrimaryStage() {
			return primaryStage;
	}
	
public boolean showPersonEditDialog(Person person) {
	try {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource("view/PersonEditDialog.fxml"));
		AnchorPane page = (AnchorPane) loader.load();
			
		Stage dialogStage = new Stage();
		dialogStage.setTitle("Edit Person");
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);
		Scene scene = new Scene(page);
		dialogStage.setScene(scene);

		PersonEditDialogController controller = loader.getController();
		controller.setDialogStage(dialogStage);
		controller.setPerson(person);
			
		dialogStage.showAndWait();
		return controller.isOkClicked();
		
	} catch (IOException e) {
		e.printStackTrace();
		return false;
		}
	}

	private ObservableList<Person> personData = FXCollections.observableArrayList();
	
	public MainApp() {
		personData.add(new Person("Hans", "Muster"));
		personData.add(new Person("Ruth", "Mueller"));
		personData.add(new Person("Heinz", "Kurz"));
		personData.add(new Person("Cornelia", "Meier"));
		personData.add(new Person("Werner", "Meyer"));
		personData.add(new Person("Lydia", "Kunz"));
		personData.add(new Person("Anna", "Best"));
		personData.add(new Person("Stefan", "Paul"));
		personData.add(new Person("Martin", "Kobe"));
	}
	
	public ObservableList<Person> getPersonData() {
		return personData;
	}
	
	public File getPersonFilePath() {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		String filePath = prefs.get("filePath", null);
		if (filePath != null) {
			return new File(filePath);
		} else {
			return null;
		}
		
	}
	
	public void setPersonFilePath(File file) {
		Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
		if (file != null) {
			prefs.put("filePath", file.getPath());
			primaryStage.setTitle("AddressApp - " + file.getName());
			
		} else {
			prefs.remove("filePath");
			primaryStage.setTitle("AddressApp");
		}
	}
	
	public void loadPersonDataFromFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
			Unmarshaller um = context.createUnmarshaller();
			PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);
			personData.clear();
			personData.addAll(wrapper.getPersons());
			setPersonFilePath(file);
			
		} catch (Exception e) {
			Dialogs.create()
					 .title("Erro")
					 .masthead("N??o foi poss??vel carregar dados do arquivo:\n"
					 		   + file.getPath()).showException(e);
		}
	}
	
	public void savePersonDataToFile(File file) {
		try {
			JAXBContext context = JAXBContext.newInstance(PersonListWrapper.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			PersonListWrapper wrapper = new PersonListWrapper();
			wrapper.setPersons(personData);
			m.marshal(wrapper, file);
			setPersonFilePath(file);
			
		} catch (Exception e) {
			if(file != null)
				Dialogs.create().title("Erro")
						.masthead("N??o foi poss??vel salvar os dados do arquivo:\n" 
							  + file.getPath()).showException(e);
		}
}
	
    public void showBirthdayStatistics() {
    	try {
    		FXMLLoader loader = new FXMLLoader();
    		loader.setLocation(MainApp.class.getResource("view/BirthdayStatistics.fxml"));
    		AnchorPane page = (AnchorPane) loader.load();
    		Stage dialogStage = new Stage();
    		dialogStage.setTitle("Birthday Statistics");
    		dialogStage.initModality(Modality.WINDOW_MODAL);
    		dialogStage.initOwner(primaryStage);
    		Scene scene = new Scene(page);
    		dialogStage.setScene(scene);
    		BirthdayStatisticsController controller = loader.getController();
    		controller.setPersonData(personData);
    		dialogStage.show();
		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
}
	
	public static void main(String[] args) {
		launch(args);
	}
		
}
