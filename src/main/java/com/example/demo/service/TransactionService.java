package com.example.demo.service;

import com.example.demo.controller.response.exception.SuperMarketException;
import com.example.demo.model.Account;
import com.example.demo.model.Product;
import com.example.demo.model.Transaction;
import com.example.demo.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransactionService {
    final TransactionRepository transactionRepository;

    final ProductService productService;
    final AccountService accountService;

    @Transactional
    public Transaction createTransaction(Long accountId, Long productId, int quantity) {
        Account account = accountService.getAccountById(accountId);
        Product product = productService.getProductById(productId);

        BigDecimal totalAmount = product.getPrice().multiply(BigDecimal.valueOf(quantity));
        if (account.getBalance().compareTo(totalAmount) < 0) throw new SuperMarketException("Insufficient balance");

        account.setBalance(account.getBalance().subtract(totalAmount));

        Transaction transaction = new Transaction();
        transaction.setAccount(account);
        transaction.setProduct(product);
        transaction.setQuantity(quantity);
        transaction.setTotalAmount(totalAmount);
        transaction.setTransactionDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }
}
