package com.example.jobs.service;

import com.example.jobs.entity.Category;
import com.example.jobs.entity.User;
import com.example.jobs.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    @Value("${images.upload.path}")
    public String imagePath;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public void deleteById(int id) {
        categoryRepository.deleteById(id);
    }

    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public void save(Category category) {
        categoryRepository.save(category);
    }

    public Category getById(int id) {
        return categoryRepository.getById(id);
    }


    public void saveCategoryPic(MultipartFile uploadedImageFile, Category category) throws IOException {
        if (!uploadedImageFile.isEmpty()) {
            String picName = System.currentTimeMillis() + "_" + uploadedImageFile.getOriginalFilename();
            File newFile = new File(imagePath + picName);
            uploadedImageFile.transferTo(newFile);
            category.setPicture(picName);

        }

        categoryRepository.save(category);
    }
}
