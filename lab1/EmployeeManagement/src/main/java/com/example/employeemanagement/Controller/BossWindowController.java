package com.example.employeemanagement.Controller;

import com.example.employeemanagement.Model.Boss;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class BossWindowController {
    private Boss boss;
    @FXML
    private Button logoutBttn;

    @FXML
    public void logoutButtonClick() {
        closeWindow();
    }

    private void closeWindow() {
        Stage thisStage = (Stage) logoutBttn.getScene().getWindow();
        thisStage.close();
    }

    @FXML
    public void sendTaskButtonClick() {
    }

    @FXML
    public void addWorkerButtonClick() {
    }

    @FXML
    public void updateWorkerButtonClick() {
    }

    @FXML
    public void deleteWorkerButtonClick() {
    }

    @FXML
    public void deleteTaskButtonClick() {
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }
}
