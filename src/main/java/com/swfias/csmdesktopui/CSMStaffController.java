package com.swfias.csmdesktopui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CSMStaffController {

    @FXML
    private Label staffNameLabel;
    public void onLogOutStaffClick(ActionEvent actionEvent) {
        try{
            CSMApplication.switchToLogInView();
        }catch (Exception e){
            System.out.println("Can't load new window");
        }
    }
    public void setStaff(String staffName) {
        staffNameLabel.setText("Welcome, " + staffName  + "!");
    }
}
