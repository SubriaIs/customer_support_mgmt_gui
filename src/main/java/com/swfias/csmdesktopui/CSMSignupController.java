package com.swfias.csmdesktopui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.swfias.daos.PersonDao;
import org.swfias.enums.PersonType;
import org.swfias.services.PersonService;

import java.net.URL;
import java.util.EnumSet;
import java.util.ResourceBundle;

public class CSMSignupController implements Initializable {

    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField signUpPasswordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;
    @FXML
    private TextField phoneNumberField;

    @FXML
    private ComboBox<PersonType> typeField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.typeField.getItems().add(PersonType.valueOf(PersonType.CUSTOMER.toString()));
    }

    public void onSaveClick(ActionEvent actionEvent) {
        PersonService personService = new PersonService(new PersonDao());
        boolean isAdded = personService.addNewPerson(typeField.getValue(), firstNameField.getText(), lastNameField.getText(), signUpPasswordField.getText(), emailField.getText(), addressField.getText(), phoneNumberField.getText());
        if (isAdded == true) {

            typeField.setValue(null);
            firstNameField.setText("");
            lastNameField.setText("");
            signUpPasswordField.setText("");
            emailField.setText("");
            addressField.setText("");
            phoneNumberField.setText("");

            Alert infoAlert = new Alert(Alert.AlertType.INFORMATION);
            infoAlert.setTitle("Saved!");
            infoAlert.setHeaderText(" Your Information has been saved!");
            infoAlert.showAndWait();
        } else {
            Alert warningAlert = new Alert(Alert.AlertType.WARNING);
            warningAlert.setTitle("Something went wrong!");
            warningAlert.setHeaderText("System Error!");
            warningAlert.setContentText("Contact system administrator.");
            warningAlert.showAndWait();
        }
    }

    public void onCloseClick(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();
            stage.close();
            CSMApplication.switchToLogInView();
        } catch (Exception e) {
            System.out.println("Can't load new window");
        }
    }


}
