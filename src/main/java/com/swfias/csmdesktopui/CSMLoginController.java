package com.swfias.csmdesktopui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.swfias.daos.PersonDao;
import org.swfias.dtos.AdminDto;
import org.swfias.dtos.CustomerDto;
import org.swfias.dtos.PersonDto;
import org.swfias.dtos.StaffDto;
import org.swfias.services.PersonService;

public class CSMLoginController {

    @FXML
    private Label statusLogin;

    @FXML
    public TextField userIdField;

    @FXML
    private TextField passwordField;



    @FXML
    protected void onLogInClick(ActionEvent event) {
        PersonService personService = new PersonService(new PersonDao());
        PersonDto personDto = personService.checkUserLogin(userIdField.getText(), passwordField.getText());
        if (personDto != null) {
            try{
                if( personDto instanceof CustomerDto){
                    CSMApplication.switchToCustomerView((CustomerDto) personDto);

                }
                else if( personDto instanceof StaffDto){
                    CSMApplication.switchToStaffView((StaffDto) personDto);
                }
                else if(personDto instanceof AdminDto){
                    CSMApplication.switchToAdminView((AdminDto) personDto);
                }
                else
                {
                    statusLogin.setText("User type is not define");
                }
            }catch (Exception e){
                System.out.println("Can't load new window");
            }
        } else {
            userIdField.setText("");
            passwordField.setText("");
            userIdField.requestFocus();
            statusLogin.setText("Incorrect Password!");
        }

    }


    public void onSignUpClick(ActionEvent actionEvent) {
        CSMApplication.switchToSignUpView(actionEvent);
    }


}