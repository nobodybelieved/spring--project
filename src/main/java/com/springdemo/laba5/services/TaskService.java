package com.springdemo.laba5.services;

import com.springdemo.laba5.entities.Task;
import com.springdemo.laba5.entities.User;
import com.springdemo.laba5.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<Task> findTaskByUserId(Long userId, Pageable pageable) {
        return taskRepository.findByUserId(userId, pageable);
    }

    public void save(Task task) {
        taskRepository.save(task);
    }


    public Page<Task> findByUserIdAndTitleContaining(Long userId, String title, Pageable pageable) {
        return taskRepository.findByUserIdAndTitleContaining(userId, title, pageable);
    }

    public Page<Task> findByUserIdAndTitleContainingAndCategoryId(Long userId, String title, Long categoryId, Pageable pageable) {
        return taskRepository.findByUserIdAndTitleContainingAndCategoryId(userId, title, categoryId, pageable);
    }

    public Page<Task> findByUserIdAndCategoryId(Long userId, Long categoryId, Pageable pageable) {
        return taskRepository.findByUserIdAndCategoryId(userId, categoryId, pageable);
    }
    public void deleteById(Long taskId) {
        Optional<Task> task = taskRepository.findById(taskId);
        if (task.isPresent()) {
            taskRepository.delete(task.get());
        } else {
            throw new RuntimeException("Task not found with id: " + taskId);
        }
    }
}
