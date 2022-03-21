package com.example.jobs.controller;

import com.example.jobs.entity.Company;
import com.example.jobs.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/company/companies")
    public String companyPage(ModelMap map) {
        Iterable<Company> companies = companyService.findAll();
        map.addAttribute("companies", companies);
        return "company";

    }

    @GetMapping("/company/delete/{id}")
    @Transactional
    public String deleteCompany(@PathVariable("id") int id) {
        companyService.deleteById(id);
        return "redirect:/company/companies";

    }

    @GetMapping("/company/add")
    public String addCompanyPage() {
        return "company/add";
    }

    @PostMapping("/company/add")
    public String addCompany(@ModelAttribute Company company) {
        companyService.save(company);
        return "redirect:/company/companies";

    }
    @GetMapping("/company/search/{id}")
    public String searchCompany(@PathVariable("id") int id){
        companyService.findById(id);
        return "job-list";

    }

}
