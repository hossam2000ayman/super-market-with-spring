package com.example.demo.model;

import com.example.demo.util.SystemUtil;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "products", indexes = {@Index(name = "idx_product_name", columnList = "name")})
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    BigDecimal price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "product-to-transactions")
    List<Transaction> transactions;

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode tree = objectMapper.valueToTree(this);
        ((ObjectNode) tree).remove("id");
        ((ObjectNode) tree).remove("transactions");


        return SystemUtil.writeObjectAsString(tree);
    }
}
