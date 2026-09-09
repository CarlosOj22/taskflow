package com.taskflow.taskflow.controller;

import com.taskflow.taskflow.model.Task;
import com.taskflow.taskflow.model.TaskPriority;
import com.taskflow.taskflow.model.TaskStatus;
import com.taskflow.taskflow.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TaskController {

    /*
    Don't create the instance manually.
    private TaskService taskService = new TaskService();
    We need to tell spring that we need TaskService instance, and
    Spring deal with it instead of us.
    */

    private final TaskService taskService;

    //Dependency Injection by constructor
    public TaskController(TaskService taskService){
        this.taskService = taskService;
    }
    @GetMapping("/tasks")
    public List<Task> getTaskList(){
        return taskService.getTasks();
    }
    @GetMapping("/hello")
    public String helloTaskFlow(){
        return "Hello TaskFlow";
    }

    @PostMapping("/tasks")
    public Task postTask(@RequestBody @Valid Task task){
        //Transform JSON into a task by taskService.createTask
        return taskService.createTask(task);
    }
    /*Find by ID. That call to Taskservice, who call taskrepository interface
    and use pre-created method.*/
    @GetMapping("/tasks/{id}")
    public Task getTaskById(@PathVariable Long id){
        return taskService.getTaskById(id);
    }

    @PutMapping("/tasks/{id}")
    public Task putTaskById(@PathVariable Long id,@RequestBody Task task) {
        return taskService.updateTask(id,task);
    }

    @DeleteMapping("/tasks/{id}")
    public void deleteTaskById(@PathVariable Long id){
        taskService.deleteTask(id);
    }

    @GetMapping("/info")
    public String info(){return "info";}
}
