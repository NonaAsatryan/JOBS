package com.example.jobs.controller;

import com.example.jobs.entity.Category;
import com.example.jobs.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.transaction.Transactional;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

   @GetMapping("/categories")
   public String categoryPage(ModelMap map) {
       Iterable<Category> categories = categoryService.findAll();
       map.addAttribute("categories", categories);
       return "category";
   }

    @GetMapping("/addCategory")
    public String addCategoryPage() {
        return "addCategory";
    }

    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute Category category) {
        categoryService.save(category);
        return "redirect:/categories";
    }

    @GetMapping("/deleteCategory/{id}")
    @Transactional
    public String deleteCategory(@PathVariable("id") int id) {
        categoryService.deleteById(id);
        return "redirect:/categories";
    }
}
