package com.example.jobs.controller;

import com.example.jobs.dto.CreateCompanyRequest;
import com.example.jobs.entity.Company;
import com.example.jobs.sequrity.CurrentUser;
import com.example.jobs.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;
    private final ModelMapper mapper;

    @GetMapping("/company/delete/{id}")
    public String deleteCompany(@PathVariable("id") int id) {
        companyService.deleteById(id);
        return "redirect:/company";

    }
    @GetMapping("/company/edit/{id}")
    public String editUserPage(ModelMap map,
                               @PathVariable("id") int id) {
        map.addAttribute("company", companyService.getById(id));

        return "employer-profile";

    }
    @GetMapping("/company")
    public String companyPage(@ModelAttribute Company company,
                              @ModelAttribute CurrentUser currentUser, ModelMap map) {
        List<Company> companies = companyService.findAll();
        map.addAttribute("companies", companies);
        return "company";
    }

    @PostMapping("/company/save")
    public String addCompany(@ModelAttribute CreateCompanyRequest createCompanyRequest, @ModelAttribute CurrentUser currentUser) {
        Company company = mapper.map(createCompanyRequest, Company.class);
        companyService.add(company);

        return "employer-profile";
    }

    @GetMapping("/company/search/{id}")
    public String searchCompany(@PathVariable("id") int id) {
        companyService.findById(id);
        return "job-list";

    }

}
