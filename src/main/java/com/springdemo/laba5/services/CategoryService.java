package com.springdemo.laba5.services;
import com.springdemo.laba5.entities.Category;
import com.springdemo.laba5.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }
}
