package com.swfias.csmdesktopui;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.swfias.dtos.AdminDto;
import org.swfias.dtos.CustomerDto;
import org.swfias.dtos.StaffDto;

import java.io.IOException;

public class CSMApplication extends Application {
    private static Stage stage;
    private static Scene logInScene;
    @Override
    public void start(Stage primaryStage) throws IOException {

        stage = primaryStage;
        switchToLoginView();
        stage.show();
    }

    public static void switchToLoginView() {
        FXMLLoader loader = new FXMLLoader(CSMApplication.class.getResource("csm-login-view.fxml"));
        try {
            Scene scene = new Scene(loader.load(), 1260,620);
            stage.setScene(scene);
            stage.setTitle("Customer Support Management - CSM");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void switchToCustomerView(CustomerDto customer) {
        FXMLLoader loader = new FXMLLoader(CSMApplication.class.getResource("csm-customer-main-view.fxml"));
        try {
            Scene scene = new Scene(loader.load(), 1260,620);
            CSMCustomerController controller = loader.getController();
            controller.setCustomer(customer);
            controller.setCustomerNameLabel(customer.getFirstName()+" "+customer.getLastName());
            stage.setScene(scene);
            stage.setTitle("Customer View - CSM");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void switchToStaffView(StaffDto staff) {
        FXMLLoader staffViewLoader = new FXMLLoader(CSMApplication.class.getResource("csm-staff-main-view.fxml"));
        try {
            Scene scene1 = new Scene(staffViewLoader.load(), 1260,620);
            CSMStaffController controller1 = staffViewLoader.getController();
            controller1.setStaffName(staff.getFirstName()+" "+staff.getLastName());
            controller1.setStaff(staff);
            stage.setScene(scene1);
            stage.setTitle("Staff View - CSM");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void switchToAdminView(AdminDto admin) {
        FXMLLoader adminViewLoader = new FXMLLoader(CSMApplication.class.getResource("csm-admin-main-view.fxml"));
        try {
            Scene scene2 = new Scene(adminViewLoader.load(), 1260,620);
            CSMAdminController controller2 = adminViewLoader.getController();
            controller2.setAdmin(admin.getFirstName()+" "+admin.getLastName());
            stage.setScene(scene2);
            stage.setTitle("Admin View - CSM");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void switchToLogInView() {
        switchToLoginView();
    }

    public static void switchToSignUpView(ActionEvent event) {
        try {
            FXMLLoader signUpViewLoader = new FXMLLoader(CSMApplication.class.getResource("csm-signup-view.fxml"));
            Scene signUpScene = new Scene(signUpViewLoader.load(), 460, 420);

            Stage signUpStage = new Stage();
            signUpStage.setScene(signUpScene);
            signUpStage.setTitle("SignUp View - CSM");
            signUpStage.initModality(Modality.WINDOW_MODAL);
            signUpStage.initOwner(stage);
            signUpStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();

    }
}