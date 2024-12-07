package me.silvernine.jwt_tutorial.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import me.silvernine.jwt_tutorial.dto.BankDTO;
import me.silvernine.jwt_tutorial.entity.Bank;
import me.silvernine.jwt_tutorial.entity.BankOption;
import me.silvernine.jwt_tutorial.repository.BankOptionRepository;
import me.silvernine.jwt_tutorial.repository.BankRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class BankService {

    private final RestTemplate restTemplate;
    private final BankRepository bankRepository;
    private final BankOptionRepository bankOptionRepository;

    @Value("${openapi.api-key}")
    private String apiKey;

    private static Map<String, String> API_URL;

    @PostConstruct
    public void init() {
        API_URL = new HashMap<>();
        API_URL.put("deposit","https://finlife.fss.or.kr/finlifeapi/depositProductsSearch.json?auth=" + apiKey + "&topFinGrpNo=020000&pageNo=1");
        API_URL.put("saving", "https://finlife.fss.or.kr/finlifeapi/savingProductsSearch.json?auth=" + apiKey + "&topFinGrpNo=020000&pageNo=1");
        API_URL.put("mortgageLoan", "https://finlife.fss.or.kr/finlifeapi/mortgageLoanProductsSearch.json?auth=" + apiKey + "&topFinGrpNo=050000&pageNo=1");
        API_URL.put("rentHouseLoan", "https://finlife.fss.or.kr/finlifeapi/mortgageLoanProductsSearch.json?auth=" + apiKey + "&topFinGrpNo=050000&pageNo=1");
    }

    @Autowired
    public BankService(BankRepository bankRepository, BankOptionRepository bankOptionRepository) {
        this.restTemplate = new RestTemplate();
        this.bankRepository = bankRepository;
        this.bankOptionRepository = bankOptionRepository;
    }

    @Transactional
    public void fetchAndSaveBankes() {
        try {
            for (Map.Entry<String, String> api : API_URL.entrySet()) {
                BankDTO response = restTemplate.getForObject(api.getValue(), BankDTO.class);

                if (response != null && response.getResult() != null) {
                    for (BankDTO.Result.Base baseInfo : response.getResult().getBaseList()) {
                        Bank bank = new Bank();
                        bank.setDclsMonth(baseInfo.getDclsMonth());
                        bank.setFinCoNo(baseInfo.getFinCoNo());
                        bank.setKorCoNm(baseInfo.getKorCoNm());
                        bank.setFinPrdtNm(baseInfo.getFinPrdtNm());
                        bank.setJoinWay(baseInfo.getJoinWay());
                        bank.setMtrtInt(baseInfo.getMtrtInt());
                        bank.setSpclCnd(baseInfo.getSpclCnd());
                        bank.setJoinDeny(baseInfo.getJoinDeny());
                        bank.setJoinMember(baseInfo.getJoinMember());
                        bank.setEtcNote(baseInfo.getEtcNote());
                        bank.setMaxLimit(baseInfo.getMaxLimit());
                        bank.setLoanInciExpn(baseInfo.getLoanInciExpn());
                        bank.setErlyRpayFee(baseInfo.getErlyRpayFee());
                        bank.setDlyRate(bank.getDlyRate());
                        bank.setLoanLmt(bank.getLoanLmt());
                        bank.setDclsStrtDay(baseInfo.getDclsStrtDay());
                        bank.setDclsEndDay(baseInfo.getDclsEndDay());
                        bank.setFinCoSubmDay(baseInfo.getFinCoSubmDay());
                        bank.setType_a(api.getKey());

                        bankRepository.save(bank);

                        for (BankDTO.Result.Option optionInfo : response.getResult().getOptionList()) {
                            if (Objects.equals(optionInfo.getFinCoNo(), bank.getFinCoNo())) {
                                BankOption option = new BankOption();
                                option.setBank(bank);
                                option.setDclsMonth(optionInfo.getDclsMonth());
                                option.setIntrRateType(optionInfo.getIntrRateType());
                                option.setIntrRateTypeNm(optionInfo.getIntrRateTypeNm());
                                option.setIntrRate(optionInfo.getIntrRate());
                                option.setIntrRate2(optionInfo.getIntrRate2());
                                option.setRsrvType(optionInfo.getRsrvType());
                                option.setRsrvTypeNm(optionInfo.getRsrvTypeNm());
                                option.setMrtgType(optionInfo.getMrtgType());
                                option.setMrtgTypeNm(optionInfo.getMrtgTypeNm());
                                option.setRpayType(optionInfo.getRpayType());
                                option.setRpayTypeNm(optionInfo.getRpayTypeNm());
                                option.setLendRateType(optionInfo.getLendRateType());
                                option.setLendRateTypeNm(optionInfo.getLendRateTypeNm());
                                option.setLendRateMin(optionInfo.getLendRateMin());
                                option.setLendRateMax(optionInfo.getLendRateMax());
                                option.setLendRateAvg(optionInfo.getLendRateAvg());

                                bankOptionRepository.save(option);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("Error fetching and saving bank: ", e);
            throw new RuntimeException("Failed to fetch and save bank", e);
        }
    }
}
