package com.example.employeemanagement.Controller;

import com.example.employeemanagement.Model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;

public class LoginWindowController {
    @FXML
    private TextField usernameTxt;
    @FXML
    private PasswordField passwordTxt;


    private EmployeeRepository employeeRepository;
    private WorkerRepository workerRepository;

    @FXML
    public void loginButtonClick() throws IOException {
        String usernamme = usernameTxt.getText();
        String password = passwordTxt.getText();


        Employee employee = checkFields(usernamme, password);
        if (employee != null) {
            FXMLLoader loader;
            Parent root;
            if(employee.getIsBoss()){
                loader = new FXMLLoader(getClass().getClassLoader().getResource("com/example/employeemanagement.views/BossWindow.fxml"));
                root = loader.load();
                BossWindowController bossCtrl = loader.<BossWindowController>getController();
                Boss boss = new Boss(employee.getName(), employee.getUsername(), employee.getPassword(), employee.getIsBoss());
                bossCtrl.setBoss(boss);
            }
            else{
                loader = new FXMLLoader(getClass().getClassLoader().getResource("com/example/employeemanagement.views/WorkerWindow.fxml"));
                root = loader.load();
                WorkerWindowController workerCtrl = loader.<WorkerWindowController>getController();
                Worker worker = new Worker(employee.getName(), employee.getUsername(), employee.getPassword(), employee.getIsBoss());
                worker.setLogInTime(LocalDateTime.now());
                workerRepository.updateLoginTime(worker);
                workerCtrl.setWorker(worker);
                workerCtrl.setWorkerRepository(workerRepository);
            }

            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.show();
            close();
        }
    }

    private Employee checkFields(String username, String password) {
        Employee employee = employeeRepository.findEmployee(username, password);
        return employee;
    }

    private void close() {
        Stage thisStage = (Stage) usernameTxt.getScene().getWindow();
        thisStage.close();
    }

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    public void setWorkerRepository(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

}
