package com.example.jobs.controller;

import com.example.jobs.entity.Category;
import com.example.jobs.entity.Job;
import com.example.jobs.service.CategoryService;
import com.example.jobs.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
public class IndexController {

    private final CategoryService categoryService;
    private final JobService jobService;


    @GetMapping("/")
    public String main(ModelMap map, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "size", defaultValue = "3") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);

        List<Category> categories = categoryService.findAll();
        map.addAttribute("categories", categories);
        Page<Job> jobPage = jobService.findAll(pageRequest);
        map.addAttribute("jobPage", jobPage);
        int totalPages = jobPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            map.addAttribute("pageNumbers", pageNumbers);
        }
        return "index";
    }

}
