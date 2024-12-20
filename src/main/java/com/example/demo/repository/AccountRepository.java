package com.example.demo.repository;

import com.example.demo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query(name = "Account.countBy")
    Long countBy();

    @Query(name = "Account.findAllAccountsInMarket")
    List<Account> findAllAccountsInMarket();

}