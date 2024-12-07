package me.silvernine.jwt_tutorial.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.silvernine.jwt_tutorial.entity.Company;
import me.silvernine.jwt_tutorial.entity.CompanyOption;
import me.silvernine.jwt_tutorial.repository.CompanyOptionRepository;
import me.silvernine.jwt_tutorial.repository.CompanyRepository;
import me.silvernine.jwt_tutorial.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
@Slf4j
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyRepository companyRepository;
    private final CompanyOptionRepository companyOptionRepository;

    @PostMapping("/fetch")
    public ResponseEntity<String> fetchAndSaveCompanies() {
        try {
            companyService.fetchAndSaveCompanies();
            return ResponseEntity.ok("Companies fetched and saved successfully");
        } catch (Exception e) {
            log.error("Error in fetchAndSaveCompanies: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch and save companies: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Company>> getAllCompanyies() {
        try {
            List<Company> companies = companyRepository.findAll();
            List<CompanyOption> companyOptions = companyOptionRepository.findAll();

            for (Company company : companies) {
                List<CompanyOption> options = companyOptions.stream()
                        .filter(option ->  option.getCompany().getCompanyId().equals(company.getCompanyId()))
                        .collect(Collectors.toList());
                company.setOptions(options);
            }
            return ResponseEntity.ok(companies);
        } catch (Exception e) {
            log.error("error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}

