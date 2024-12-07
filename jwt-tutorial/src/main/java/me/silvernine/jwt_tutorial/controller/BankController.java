package me.silvernine.jwt_tutorial.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.silvernine.jwt_tutorial.entity.Bank;
import me.silvernine.jwt_tutorial.entity.BankOption;
import me.silvernine.jwt_tutorial.repository.BankOptionRepository;
import me.silvernine.jwt_tutorial.repository.BankRepository;
import me.silvernine.jwt_tutorial.service.BankService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/bank")
@RequiredArgsConstructor
@Slf4j
public class BankController {

    private final BankService bankService;
    private final BankRepository bankRepository;
    private final BankOptionRepository bankOptionRepository;

    @PostMapping("/save")
    public ResponseEntity<String> saveBank() {
        try {
            bankService.fetchAndSaveBankes();
            return ResponseEntity.ok("bank saved");
        } catch (Exception e) {
            log.error("Error in fetchAndSaveBankies: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to fetch and save bankies: " + e.getMessage());
        }
    }

    @GetMapping("/bank")
    public ResponseEntity<List<Bank>> getAllBankies() {
        try {
            List<Bank> banks = bankRepository.findAll();
            List<BankOption> bankOptions = bankOptionRepository.findAll();
            for (Bank bank : banks) {
                List<BankOption> options = bankOptions.stream()
                        .filter(option -> option.getBank().getBankId().equals(bank.getBankId()))
                        .collect(Collectors.toList());
                bank.setOptions(options);
            }
            return ResponseEntity.ok(banks);
        } catch (Exception e) {
            log.error("error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
