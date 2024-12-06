package com.example.demo.controller;

import com.example.demo.controller.dto.AccountDto;
import com.example.demo.controller.dto.ProductDto;
import com.example.demo.model.Account;
import com.example.demo.model.Product;
import com.example.demo.model.Transaction;
import com.example.demo.service.AccountService;
import com.example.demo.service.HistoryService;
import com.example.demo.service.ProductService;
import com.example.demo.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/market")
@RequiredArgsConstructor
public class SuperMarketController {

    final ProductService productService;
    final TransactionService transactionService;
    final AccountService accountService;
    final HistoryService historyService;

    @PostMapping("account/enter")
    public Account enterMarket(
            @RequestParam(required = false, defaultValue = "0", name = "account-id") Long accountId,
            @RequestBody(required = false) AccountDto accountDto
    ) {
        return accountService.enterMarket(accountId, accountDto);
    }

    @PostMapping("account/exit/{accountId}")
    public String exitMarket(@PathVariable Long accountId) {
        accountService.exitMarket(accountId);
        return "Account exit successfully";
    }

    @PutMapping("account/change/{id}")
    public Account updateAccount(@PathVariable Long id, @RequestBody AccountDto accountDto) {
        return accountService.updateAccount(id, accountDto);
    }

    @GetMapping("accounts")
    public List<Account> getAllAccounts() {
        return historyService.getAccountsInMarket();
    }

    @GetMapping("account/count")
    public Long getNumberOfAccountsInMarket() {
        return historyService.getNumberOfAccount();
    }


    @PostMapping("product/add")
    public Product addProduct(@RequestBody ProductDto productDto) {
        return productService.addProduct(productDto);
    }

    @PutMapping("product/update/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody ProductDto productDto) {
        return productService.updateProduct(id, productDto);
    }

    @DeleteMapping("product/delete/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/products/get/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PostMapping("/buy")
    public Transaction buyProduct(@RequestParam("account-id") Long accountId, @RequestParam("product-id") Long productId, @RequestParam int quantity) {
        return transactionService.createTransaction(accountId, productId, quantity);
    }

}
