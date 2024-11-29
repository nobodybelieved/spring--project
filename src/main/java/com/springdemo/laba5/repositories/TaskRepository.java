package com.springdemo.laba5.repositories;

import com.springdemo.laba5.entities.Task;
import com.springdemo.laba5.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);

    List<Task> findByCategoryId(Long categoryId);

    List<Task> findByUser(User user);

    Page<Task> findByUserId(Long userId, Pageable pageable);

    Page<Task> findByTitleContaining(String title, Pageable pageable);

    Page<Task> findByStatus(String status, Pageable pageable);

    Page<Task> findByTitleContainingAndStatus(String title, String status, Pageable pageable);

    Page<Task> findByUserIdAndTitleContainingAndStatus(Long userId, String title, String status, Pageable pageable);

    Page<Task> findByUserIdAndTitleContaining(Long userId, String title, Pageable pageable);

    Page<Task> findByUserIdAndStatus(Long userId, String status, Pageable pageable);

    Page<Task> findByUserIdAndTitleContainingAndCategoryId(Long userId, String title, Long categoryId, Pageable pageable);

    Page<Task> findByUserIdAndCategoryId(Long userId, Long categoryId, Pageable pageable);



}

