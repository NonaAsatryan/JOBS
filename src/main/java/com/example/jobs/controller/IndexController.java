package com.example.jobs.controller;

import com.example.jobs.entity.Category;
import com.example.jobs.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final CategoryService categoryService;


    @GetMapping("/")
    public String main(@ModelAttribute Category category, ModelMap map) {
        List<Category> categories = categoryService.findAll();
        map.addAttribute("categories", categories);

        return "index";
    }

}
