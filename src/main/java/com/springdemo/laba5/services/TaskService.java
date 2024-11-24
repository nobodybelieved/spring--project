package com.springdemo.laba5.services;

import com.springdemo.laba5.entities.Task;
import com.springdemo.laba5.entities.User;
import com.springdemo.laba5.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public List<Task> findTasksByUser(User user) {
        return taskRepository.findByUser(user);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }
    public List<Task> findTaskByUserId(Long id) {
        return taskRepository.findByUserId(id);

    }

    public List<Task> getAllTasks() {
        User currentUser = getCurrentUser();
        return taskRepository.findByUser(currentUser);
    }
    private User getCurrentUser() {
        return new User();
    }
}
