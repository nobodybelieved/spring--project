package com.springdemo.laba5.repositories;

import com.springdemo.laba5.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
        User findByEmail(String email);

        User findByName(String username);

        User findByEmailAndPassword(String email, String password);

        User findById(long id);

}
