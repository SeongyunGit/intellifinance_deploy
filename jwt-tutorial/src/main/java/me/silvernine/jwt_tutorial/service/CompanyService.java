package me.silvernine.jwt_tutorial.service;

import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import me.silvernine.jwt_tutorial.dto.CompanyDTO;
import me.silvernine.jwt_tutorial.entity.Company;
import me.silvernine.jwt_tutorial.entity.CompanyOption;
import me.silvernine.jwt_tutorial.repository.CompanyOptionRepository;
import me.silvernine.jwt_tutorial.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Service
@Slf4j
public class CompanyService {
    private final RestTemplate restTemplate;
    private final CompanyRepository companyRepository;
    private final CompanyOptionRepository optionRepository;

    @Value("${openapi.api-key}")
    private String apiKey;

    private static String API_URL;

    @PostConstruct
    public void init() {
        API_URL = "https://finlife.fss.or.kr/finlifeapi/companySearch.json?auth=" + apiKey + "&topFinGrpNo=020000&pageNo=1";
    }


    @Autowired
    public CompanyService(CompanyRepository companyRepository,
                          CompanyOptionRepository optionRepository) {
        this.restTemplate = new RestTemplate();
        this.companyRepository = companyRepository;
        this.optionRepository = optionRepository;
    }

    @Transactional
    public void fetchAndSaveCompanies() {
        try {
            CompanyDTO response = restTemplate.getForObject(API_URL, CompanyDTO.class);
            if (response != null && response.getResult() != null) {

                for (CompanyDTO.Result.Base baseInfo : response.getResult().getBaseList()) {
                    Company company = new Company();
                    company.setFinCoNo(baseInfo.getFinCoNo());
                    company.setDclsMonth(baseInfo.getDclsMonth());
                    company.setKorCoNm(baseInfo.getKorCoNm());
                    company.setDclsChrgMan(baseInfo.getDclsChrgMan());
                    company.setHompUrl(baseInfo.getHompUrl());
                    company.setCalTel(baseInfo.getCalTel());
                    companyRepository.save(company);


                    for (CompanyDTO.Result.Option optionInfo : response.getResult().getOptionList()) {
                        if (Objects.equals(optionInfo.getFinCoNo(), company.getFinCoNo())) {
                            CompanyOption option = new CompanyOption();
                            option.setCompany(company);
                            option.setAreaCd(optionInfo.getAreaCd());
                            option.setAreaNm(optionInfo.getAreaNm());
                            option.setExisYn(optionInfo.getExisYn());
                            option.setDclsMonth(optionInfo.getDclsMonth());

                            optionRepository.save(option);
                        }
                    }
                }
            }
            else {
                System.out.println("저장 안됨");
            }
        } catch (Exception e) {
            log.error("Error fetching and saving companies: ", e);
            throw new RuntimeException("Failed to fetch and save companies", e);
        }
    }
}