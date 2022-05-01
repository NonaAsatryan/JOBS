package com.example.jobs.controller;

import com.example.jobs.dto.CreateCompanyRequest;
import com.example.jobs.entity.Category;
import com.example.jobs.entity.Company;
import com.example.jobs.sequrity.CurrentUser;
import com.example.jobs.service.CategoryService;
import com.example.jobs.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final CategoryService categoryService;
    private final ModelMapper mapper;

    @GetMapping("/company/delete/{id}")
    public String deleteCompany(@PathVariable("id") int id) {
        companyService.deleteById(id);
        return "redirect:/company";

    }

    @GetMapping("/company/edit/{id}")
    public String editCompanyPage(ModelMap map,
                                  @PathVariable("id") int id) {
        map.addAttribute("company", companyService.getById(id));

        return "employer-profile";

    }

    @GetMapping("/company")
    public String companyPage(@ModelAttribute Company company,
                              @ModelAttribute CurrentUser currentUser, ModelMap map) {
        List<Company> companies = companyService.findByUserId(currentUser.getUser().getId());
        map.addAttribute("companies", companies);
        return "company";
    }

    @PostMapping("/company/save")
    public String addCompany(@ModelAttribute @Valid CreateCompanyRequest createCompanyRequest,
                             BindingResult bindingResult,
                             @ModelAttribute CurrentUser currentUser,
                             @ModelAttribute Category category, ModelMap map) {

        if (bindingResult.hasErrors()) {
            List<String> errors = new ArrayList<>();
            for (ObjectError allError : bindingResult.getAllErrors()) {
                errors.add(allError.getDefaultMessage());
            }
            map.addAttribute("errors", errors);
            map.addAttribute("categories", categoryService.findAll());
            return "employer-profile";
        } else {
            Company company = mapper.map(createCompanyRequest, Company.class);
            companyService.add(company,currentUser.getUser());

            return "redirect:/company";
        }
    }

    @GetMapping("/company/search/{id}")
    public String searchCompany(@PathVariable("id") int id) {
        companyService.findById(id);
        return "job-list";

    }

}
