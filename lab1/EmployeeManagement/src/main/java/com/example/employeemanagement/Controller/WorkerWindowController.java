package com.example.employeemanagement.Controller;

import com.example.employeemanagement.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.time.LocalDateTime;
import java.util.List;

public class WorkerWindowController implements Observer {
    ObservableList<Task> model = FXCollections.observableArrayList();
    private Worker worker;
    private WorkerRepository workerRepository;
    private TaskRepository taskRepository;
    @FXML
    public TableColumn<Task, String> nameColumn;
    @FXML
    public TableColumn<Task, String> descriptionColumn;
    @FXML
    public TableColumn<Task, String> statusColumn;
    @FXML
    public TableView<Task> taskTable;
    @FXML
    public TextField taskNameTxt;

    @FXML
    public void logoutButtonClick() {
        worker.setLogOutTime(LocalDateTime.now());
        workerRepository.updateLogoutTime(worker);
        closeWindow();
    }
    @FXML
    public void markTaskDone(){
        String taskName = taskNameTxt.getText();
        taskRepository.updateStatusTask(taskName);
        model.setAll((List<Task>) taskRepository.getById(worker.getUsername()));
    }

    private void closeWindow() {
        Stage thisStage = (Stage) taskTable.getScene().getWindow();
        taskRepository.removeObserver(this);
        thisStage.close();
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public void setWorkerRepository(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        initializeTable();
        model.setAll((List<Task>) taskRepository.getById(worker.getUsername()));
    }

    public void initializeTable(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        taskTable.setItems(model);
    }


    @Override
    public void update() {
        model.setAll((List<Task>) taskRepository.getById(worker.getUsername()));
    }
}
