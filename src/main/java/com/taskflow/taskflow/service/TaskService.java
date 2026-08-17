package com.taskflow.taskflow.service;

import com.taskflow.taskflow.model.Task;
import com.taskflow.taskflow.model.TaskPriority;
import com.taskflow.taskflow.model.TaskStatus;
import com.taskflow.taskflow.repository.TaskRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    //Constructor (Inyeccion de depedencias)
    public TaskService(TaskRepository taskRepository){
        this.taskRepository=taskRepository;
    }
    public List<Task> getTasks(){
        return taskRepository.findAll();
    }

    public Task createTask(Task task){
        return taskRepository.save(task);
    }

    public Task getTaskById(Long id){

        return taskRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    }

    public Task updateTask(Long id, Task task){
        //First check if exist
        Optional<Task> existingTask = taskRepository.findById(id);
        if(existingTask.isPresent()){
            Task newTask = existingTask.get();
            newTask.setName(task.getName());
            newTask.setDescription(task.getDescription());
            newTask.setStatus(task.getStatus());
            newTask.setPriority(task.getPriority());
            return taskRepository.save(newTask);
        }else{
            //If doesnt exist, throw exception
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteTask(Long id){
        Optional<Task> existingTask = taskRepository.findById(id);
        if(existingTask.isPresent()){
            taskRepository.deleteById(id);
        }else{
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
}


