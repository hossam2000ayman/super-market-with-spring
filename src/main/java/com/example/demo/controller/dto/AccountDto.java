package com.example.demo.controller.dto;

import com.example.demo.model.embedded.Address;
import com.example.demo.model.embedded.Members;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
public class AccountDto {
    String name;
    String email;
    String username;
    String password;
    BigDecimal balance;
    Address address;
    List<Members> members;
}
