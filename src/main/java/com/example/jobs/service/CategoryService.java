package com.example.jobs.service;

import com.example.jobs.entity.Category;
import com.example.jobs.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public Category getById(int id) {
        return categoryRepository.getById(id);
    }
}
