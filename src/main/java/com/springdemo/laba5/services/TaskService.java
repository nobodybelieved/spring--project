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
    public Page<Task> findTaskByUserId(Long userId, Pageable pageable) {
        return taskRepository.findByUserId(userId, pageable);
    }

    public List<Task> getAllTasks() {
        User currentUser = getCurrentUser();
        return taskRepository.findByUser(currentUser);
    }
    private User getCurrentUser() {
        return new User();
    }

    public void save(Task task) {
        taskRepository.save(task);
    }

    public Page<Task> getTasksByFilters(String title, String status, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        if (title != null && !title.isEmpty() && status != null && !status.isEmpty()) {
            return taskRepository.findByTitleContainingAndStatus(title, status, pageRequest);
        } else if (title != null && !title.isEmpty()) {
            return taskRepository.findByTitleContaining(title, pageRequest);
        } else if (status != null && !status.isEmpty()) {
            return taskRepository.findByStatus(status, pageRequest);
        } else {
            return taskRepository.findAll(pageRequest);
        }
    }





    public Page<Task> findByUserIdAndTitleContainingAndStatus(Long userId, String title, String status, Pageable pageable) {
        return taskRepository.findByUserIdAndTitleContainingAndStatus(userId, title, status, pageable);
    }

    public Page<Task> findByUserIdAndTitleContaining(Long userId, String title, Pageable pageable) {
        return taskRepository.findByUserIdAndTitleContaining(userId, title, pageable);
    }

    public Page<Task> findByUserIdAndStatus(Long userId, String status, Pageable pageable) {
        return taskRepository.findByUserIdAndStatus(userId, status, pageable);
    }

    public Page<Task> findByUserIdAndTitleContainingAndCategoryId(Long userId, String title, Long categoryId, Pageable pageable) {
        return taskRepository.findByUserIdAndTitleContainingAndCategoryId(userId, title, categoryId, pageable);
    }

    public Page<Task> findByUserIdAndCategoryId(Long userId, Long categoryId, Pageable pageable) {
        return taskRepository.findByUserIdAndCategoryId(userId, categoryId, pageable);
    }

    public Page<Task> findFilteredTasks(Long userId, String title, Long categoryId, Pageable pageable) {
        if (title != null && categoryId != null) {
            return taskRepository.findByUserIdAndTitleContainingAndCategoryId(userId, title, categoryId, pageable);
        } else if (title != null) {
            return taskRepository.findByUserIdAndTitleContaining(userId, title, pageable);
        } else if (categoryId != null) {
            return taskRepository.findByUserIdAndCategoryId(userId, categoryId, pageable);
        } else {
            return taskRepository.findByUserId(userId, pageable);
        }
    }


}
