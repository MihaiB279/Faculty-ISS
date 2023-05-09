package com.example.employeemanagement.Controller;

import com.example.employeemanagement.Model.Employee;
import com.example.employeemanagement.Model.EmployeeRepository;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginWindowController {
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;


    private EmployeeRepository employeeRepository;

    @FXML
    public void loginBttnClick() throws IOException {
        String usernamme = usernameTxt.getText();
        String password = passwordTxt.getText();


        int result = checkFields(usernamme, password);
        if (result != -1) {
            FXMLLoader loader = switch (result) {
                case 1 ->
                        new FXMLLoader(getClass().getClassLoader().getResource("com/example/employeemanagement.views/BossWindow.fxml"));
                case 2 ->
                        new FXMLLoader(getClass().getClassLoader().getResource("com/example/employeemanagement.views/WorkerWindow.fxml"));
                default -> null;
            };

            Parent root = loader.load();
            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.show();
            close();
        }
    }

    private int checkFields(String username, String password) {
        Employee employee = employeeRepository.findEmployee(username, password);
        if (employee == null)
            return -1;
        if (employee.getIsBoss())
            return 1;
        else return 2;
    }

    public void setRepo(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    private void close() {
        Stage thisStage = (Stage) usernameTxt.getScene().getWindow();
        thisStage.close();
    }
}
