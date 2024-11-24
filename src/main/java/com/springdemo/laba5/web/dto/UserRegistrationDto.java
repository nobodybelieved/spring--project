package com.springdemo.laba5.web.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class UserRegistrationDto {
    private String name;
    private int age;
    private String email;
    private String password;
}
