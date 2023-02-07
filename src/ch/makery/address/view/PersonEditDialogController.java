package ch.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import ch.makery.address.model.Person;
import ch.makery.address.util.DateUtil;

public class PersonEditDialogController {
	
	@FXML
	private TextField firstNameField;
	@FXML
	private TextField lastNameField;
	@FXML
	private TextField streetField;
	@FXML
	private TextField postalCodeField;
	@FXML
	private TextField cityField;
	@FXML
	private TextField birthdayField;
	@FXML
	private Stage dialogStage;
	@FXML
	private Person person;
	@FXML
	private boolean okClicked = false;
	
	@FXML
	private void intialize() {
	}
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	public void setPerson(Person person) {
		this.person = person;
		firstNameField.setText(person.getFirstName());
		lastNameField.setText(person.getLastName());
		streetField.setText(person.getStreet());
		postalCodeField.setText(Integer.toString(person.getPostalCode()));
		birthdayField.setText(DateUtil.format(person.getBirthday()));
		birthdayField.setPromptText("dd.mm.yyyy");
	}
	public boolean isOkClicked() {
		return okClicked;	
	}
	
	@FXML
	private void handleOk() {
		if (isInputValid()) {
			person.setFirstName(firstNameField.getText());
			person.setLastName(lastNameField.getText());
			person.setStreet(streetField.getText());
			person.setPostalCode(Integer.parseInt(postalCodeField.getText()));
			person.setBirthday(DateUtil.parse(birthdayField.getText()));
		}	
			okClicked = true;
			dialogStage.close();
		}
		
	@FXML
	private void handleCancel() {
		dialogStage.close();
	}


private boolean isInputValid() {
		String errorMessage = "";
		
		if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
			errorMessage += "Nome Inválido!\n";
		}
		if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
			errorMessage += "Sobrenome Inválido!\n";
		}
		if (streetField.getText() == null || streetField.getText().length() == 0) {
			errorMessage += "Rua Inválida!\n";
		}	
		if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
			errorMessage += "Código Postal Inválido!\n";
		} else {
			try {
			Integer.parseInt(postalCodeField.getText());
			
		} catch (NumberFormatException e) {
			errorMessage += "Código Postal Inválido (deve ser um inteiro)!\n";
		}
	}
		
	if (cityField.getText() == null || cityField.getText().length() == 0) {
		errorMessage += "Cidade Inválida!\n";
	}
	if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
		errorMessage += "Aniversário Inválido!\n";
	} else {
		if (!DateUtil.validDate(birthdayField.getText())) {
			errorMessage += "Aniverário Inválido. Use o formato dd.mm.yyyy!\n";
		}
	}
	
	if (errorMessage.length() == 0) {
		return true;
	} else {
		Alert alert = new Alert(AlertType.ERROR);
			  alert.setTitle("Campos Inválidos");
			  alert.setHeaderText("Por Favor, corrija os campos inválidos");
			  alert.setContentText(errorMessage);
		alert.showAndWait();
		
	  return false;
	}
  }
}
