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
    private TaskRepository taskRepository;

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
                workerRepository.addObserver(bossCtrl);
                Boss boss = new Boss(employee.getName(), employee.getUsername(), employee.getPassword(), employee.getIsBoss());
                bossCtrl.setBoss(boss);
                bossCtrl.setEmployeeRepository(employeeRepository);
                bossCtrl.setWorkerRepository(workerRepository);
                bossCtrl.setTaskRepository(taskRepository);
            }
            else{
                loader = new FXMLLoader(getClass().getClassLoader().getResource("com/example/employeemanagement.views/WorkerWindow.fxml"));
                root = loader.load();
                WorkerWindowController workerCtrl = loader.<WorkerWindowController>getController();
                taskRepository.addObserver(workerCtrl);
                Worker worker = new Worker(employee.getName(), employee.getUsername(), employee.getPassword(), employee.getIsBoss());
                worker.setLogInTime(LocalDateTime.now());
                workerRepository.updateLoginTime(worker);
                workerCtrl.setWorker(worker);
                workerCtrl.setWorkerRepository(workerRepository);
                workerCtrl.setTaskRepository(taskRepository);
            }

            Stage loginStage = new Stage();
            loginStage.setScene(new Scene(root));
            loginStage.show();
        }
    }

    private Employee checkFields(String username, String password) {
        Employee employee = employeeRepository.findEmployee(username, password);
        return employee;
    }

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    public void setWorkerRepository(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
}
