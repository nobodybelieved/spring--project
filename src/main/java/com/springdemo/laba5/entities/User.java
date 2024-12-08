package com.springdemo.laba5.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "USERS_TB")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String imageLink;
    @NotNull
    @Min(18)
    private int age;
    private String email;
    private String password;
    @Column(name = "account_creation_date")
    private Date accountCreationDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();

    public User(String name, int age, String email, String password, Date accountCreationDate) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.accountCreationDate = accountCreationDate; 
    }
    public void addTask(Task task) {
        tasks.add(task);
        task.setUser(this);
    }
    public void removeTask(Task task) {
        tasks.remove(task);
        task.setUser(null);
    }
}
