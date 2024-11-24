package com.springdemo.laba5.repositories;

import com.springdemo.laba5.entities.Task;
import com.springdemo.laba5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByUserId(Long userId);
    List<Task> findByCategoryId(Long categoryId);
    List<Task> findByUser(User user);

}

