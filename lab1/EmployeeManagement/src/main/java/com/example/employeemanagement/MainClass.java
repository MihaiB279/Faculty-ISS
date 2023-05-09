package com.example.employeemanagement;

import com.example.employeemanagement.Controller.LoginWindowController;
import com.example.employeemanagement.Model.EmployeeRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainClass extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {


        EmployeeRepository employeeRepository = new EmployeeRepository();
        FXMLLoader loaderLogin = new FXMLLoader(getClass().getClassLoader().getResource("com/example/employeemanagement.views/LogIn.fxml"));
        Parent root = loaderLogin.load();
        LoginWindowController loginCtrl = loaderLogin.<LoginWindowController>getController();
        loginCtrl.setRepo(employeeRepository);
        Stage loginStage = new Stage();
        loginStage.setScene(new Scene(root));
        loginStage.show();
    }
}