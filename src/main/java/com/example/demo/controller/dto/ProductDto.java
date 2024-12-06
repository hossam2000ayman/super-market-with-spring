package com.example.demo.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class ProductDto {
    String name;
    BigDecimal price;
}
