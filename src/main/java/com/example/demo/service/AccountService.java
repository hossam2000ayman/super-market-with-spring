package com.example.demo.service;

import com.example.demo.controller.dto.AccountDto;
import com.example.demo.controller.response.exception.SuperMarketException;
import com.example.demo.model.Account;
import com.example.demo.repository.AccountRepository;
import com.example.demo.util.SystemUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {
    final AccountRepository accountRepository;


    public Account enterMarket(Long accountId, AccountDto accountDto) {
        if (accountDto == null && accountId != 0) {
            Account account = getAccountById(accountId);
            if (account.isInMarket()) {
                throw new SuperMarketException("Your account is already in super market");
            }
            account.setInMarket(true);
            return accountRepository.saveAndFlush(account);
        } else {
            return addAccount(accountDto);
        }
    }

    public Account addAccount(AccountDto accountDto) {
        Account account = SystemUtil.castPropsToDestination(accountDto, Account.class);
        account.setInMarket(true);
        return accountRepository.save(account);
    }


    public Account updateAccount(Long id, AccountDto newAccountDto) {
        Account account = getAccountById(id);
        if (!account.isInMarket())
            throw new SuperMarketException("Cannot change your account while you outside of market");
        SystemUtil.copyPropsFromSrcToDest(newAccountDto, account);
        log.info("Account :: {}", account);
        return accountRepository.saveAndFlush(account);
    }

    public void exitMarket(Long id) {
        Account account = getAccountById(id);
        account.setInMarket(false);
        accountRepository.saveAndFlush(account);
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow(() -> new SuperMarketException("Account not Found"));
    }
}
