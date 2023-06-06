package com.example.employeemanagement.Controller;

import com.example.employeemanagement.Model.Task;
import com.example.employeemanagement.Model.TaskRepository;
import com.example.employeemanagement.Model.Worker;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class TasksWindowController {
    String workerName;
    ObservableList<Task> model = FXCollections.observableArrayList();
    @FXML
    public TableColumn<Task, String> nameColumn;
    @FXML
    public TableColumn<Task, String> statusColumn;
    @FXML
    public TableView<Task> taskTable;
    private TaskRepository taskRepository;

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }
    public void setTaskRepository(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
        initializeFields();
        model.setAll((List<Task>) taskRepository.getById(workerName));
    }
    public void initializeFields(){
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        taskTable.setItems(model);
    }
}
