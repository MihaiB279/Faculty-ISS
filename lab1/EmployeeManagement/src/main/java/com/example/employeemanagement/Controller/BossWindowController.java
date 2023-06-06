package com.example.employeemanagement.Controller;

import com.example.employeemanagement.Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class BossWindowController implements Observer {
    private Boss boss;
    ObservableList<Worker> model = FXCollections.observableArrayList();
    @FXML
    private TextField nameTxt;
    @FXML
    private TextField usernameTxt;
    @FXML
    private TextField passwordTxt;
    @FXML
    private TextArea taskDescription;
    @FXML
    private TextField taskNameTxt;
    @FXML
    public TableColumn<Worker, String> nameColumn;
    @FXML
    public TableColumn<Worker, String> timeColumn;
    @FXML
    public TableColumn<Worker, String> statusColumn;
    @FXML
    public TableView<Worker> workerTable;


    private WorkerRepository workerRepository;
    private EmployeeRepository employeeRepository;
    private TaskRepository taskRepository;

    public void setWorkerRepository(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
        initializeTable();
        model.setAll((List<Worker>) workerRepository.getActiveWorkers());
    }

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    @FXML
    public void logoutButtonClick() {
        closeWindow();
    }

    private void closeWindow() {
        Stage thisStage = (Stage) nameTxt.getScene().getWindow();
        workerRepository.removeObserver(this);
        thisStage.close();
    }

    public void initializeTable(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<>("loginHour"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        workerTable.setItems(model);
    }

    @FXML
    public void sendTaskButtonClick() {
        String username = usernameTxt.getText();
        String descriptionText = taskDescription.getText();
        String taskName = taskNameTxt.getText();

        Task task = new Task(taskName,descriptionText,username);
        taskRepository.saveTask(task);
    }

    @FXML
    public void addWorkerButtonClick() {
        String name = nameTxt.getText();
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        Worker worker =  new Worker(name, username, password,false);
        workerRepository.addWorker(worker);
    }

    @FXML
    public void updateWorkerButtonClick() {
        String name = nameTxt.getText();
        String username = usernameTxt.getText();
        String password = passwordTxt.getText();

        Worker employee =  new Worker(name, username, password,false);
        workerRepository.updateWorker(employee);
    }

    @FXML
    public void deleteWorkerButtonClick() {
        String username = usernameTxt.getText();
        workerRepository.deleteWorker(username);
    }
    @FXML
    public void seeTasksForWorker() throws IOException {
        String username = usernameTxt.getText();
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("com/example/employeemanagement.views/TaskWindow.fxml"));
        Parent root = loader.load();
        TasksWindowController tasksCtrl = loader.<TasksWindowController>getController();
        tasksCtrl.setWorkerName(username);
        tasksCtrl.setTaskRepository(taskRepository);

        Stage tasksStage = new Stage();
        tasksStage.setScene(new Scene(root));
        tasksStage.show();
    }

    @FXML
    public void deleteTaskButtonClick() {
        String name = taskNameTxt.getText();
        taskRepository.deleteTask(name);
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
    }

    @Override
    public void update() {
        model.setAll((List<Worker>) workerRepository.getActiveWorkers());
    }
}
