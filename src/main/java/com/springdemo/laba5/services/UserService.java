package com.springdemo.laba5.services;

import com.springdemo.laba5.entities.User;
import com.springdemo.laba5.repositories.UserRepository;
import com.springdemo.laba5.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
     User save(UserRegistrationDto registrationDto);

}
