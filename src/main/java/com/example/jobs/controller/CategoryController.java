package com.example.jobs.controller;

import com.example.jobs.entity.Category;
import com.example.jobs.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    @Value("${images.upload.path}")
    public String imagePath;


    @GetMapping("/category/delete/{id}")
    public String deleteCategory(@PathVariable("id") int id) {
        categoryService.deleteById(id);
        return "redirect:/category";

    }

    @GetMapping("/category/edit/{id}")
    public String editCategoryPage(ModelMap map,
                                   @PathVariable("id") int id) {
        map.addAttribute("category", categoryService.getById(id));

        return "admin-profile";

    }

    @GetMapping("/category")
    public String categoryPage(@ModelAttribute Category category, ModelMap map) {
        List<Category> categories = categoryService.findAll();
        map.addAttribute("categories", categories);
        return "category";
    }

    @PostMapping("/category/save")
    public String saveCategory(@ModelAttribute Category category, @RequestParam("picName") MultipartFile uploadedImageFile) throws IOException {
        categoryService.create(category);
        categoryService.saveCategoryPic(uploadedImageFile, category);
        return "admin-profile";
    }

    @GetMapping(value = "/getCategoryPic", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody
    byte[] getImage(@RequestParam("picName") String picName) throws IOException {
        InputStream inputStream = new FileInputStream(imagePath + picName);
        return IOUtils.toByteArray(inputStream);
    }

}
