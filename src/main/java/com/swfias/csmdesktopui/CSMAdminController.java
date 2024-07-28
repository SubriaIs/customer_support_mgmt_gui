package com.swfias.csmdesktopui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.swfias.daos.PersonDao;
import org.swfias.enums.PersonType;
import org.swfias.services.PersonService;

import java.net.URL;
import java.util.EnumSet;
import java.util.ResourceBundle;

public class CSMAdminController implements Initializable {

    @FXML
    private Label adminNameLabel;
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField passwordField;

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
        this.typeField.getItems().addAll(EnumSet.allOf(PersonType.class));
    }

    public void onLogOutAdminClick(ActionEvent actionEvent) {
        try{
            CSMApplication.switchToLogInView();
        }catch (Exception e){
            System.out.println("Can't load new window");
        }
    }
    public void setAdmin(String adminName) {
        adminNameLabel.setText("Welcome, " + adminName  + "!");
    }

    public void onCreateNewEmployee(ActionEvent actionEvent) {
        PersonService personService = new PersonService(new PersonDao());
        boolean isAdded = personService.addNewPerson(typeField.getValue(), firstNameField.getText(), lastNameField.getText(), passwordField.getText(), emailField.getText(), addressField.getText(), phoneNumberField.getText());
        if (isAdded == true) {

            typeField.setValue(null);
            firstNameField.setText("");
            lastNameField.setText("");
            passwordField.setText("");
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
}
