package com.example.demo.model;

import com.example.demo.util.SystemUtil;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Setter
@Getter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    Integer quantity;
    BigDecimal totalAmount;
    @CreationTimestamp
    LocalDateTime transactionDate;


    @ManyToOne
    @JsonIgnoreProperties({"email", "password", "inMarket", "name", "address", "members", "transactions", "balance"})
//    @JsonBackReference(value = "account-to-transactions")
    @JoinColumn(name = "account_id", nullable = false)
    Account account;
    @ManyToOne
    @JsonBackReference(value = "product-to-transactions")
    @JoinColumn(name = "product_id", nullable = false)
    Product product;

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode tree = objectMapper.valueToTree(this);
        ((ObjectNode) tree).remove("id");
        ((ObjectNode) tree).remove("product");
        ((ObjectNode) tree).remove("account");

        return SystemUtil.writeObjectAsString(tree);
    }

}
