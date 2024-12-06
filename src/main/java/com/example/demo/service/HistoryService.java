package com.example.demo.service;

import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryService {
    final AccountRepository accountRepository;

    public Long getNumberOfAccount() {
        return accountRepository.countBy();
    }

    public List<Account> getAccountsInMarket() {
        return accountRepository.findAllAccountsInMarket();
    }
}
