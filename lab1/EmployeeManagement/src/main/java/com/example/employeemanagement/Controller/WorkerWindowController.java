package com.example.employeemanagement.Controller;

import com.example.employeemanagement.Model.Worker;
import com.example.employeemanagement.Model.WorkerRepository;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.time.LocalDateTime;

public class WorkerWindowController {
    private Worker worker;
    private WorkerRepository workerRepository;
    @FXML
    public Button logoutBttn;

    @FXML
    public void logoutButtonClick() {
        worker.setLogOutTime(LocalDateTime.now());
        workerRepository.updateLogoutTime(worker);
        closeWindow();
    }

    private void closeWindow() {
        Stage thisStage = (Stage) logoutBttn.getScene().getWindow();
        thisStage.close();
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public void setWorkerRepository(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }
}
