package com.example.demo.model;

import com.example.demo.model.embedded.Address;
import com.example.demo.model.embedded.Members;
import com.example.demo.util.SystemUtil;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@ToString
@Entity
@Table(name = "accounts", indexes = {
        @Index(name = "idx_account_username", columnList = "username")
}, uniqueConstraints = {
        @UniqueConstraint(name = "uc_account_email_username", columnNames = {"email", "username"})
})
@NamedQueries({
        @NamedQuery(name = "Account.countBy", query = "select count(a) from Account a"),
        @NamedQuery(name = "Account.findAllAccountsInMarket", query = "select a from Account a where a.inMarket = true")
})
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_gen")
    @SequenceGenerator(name = "account_gen", initialValue = 1, allocationSize = 100)
    Long id;

    String name;
    String email;
    String username;
    String password;
    BigDecimal balance;
    boolean inMarket;

    @Embedded
    Address address;

    @ElementCollection
    List<Members> members;


    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference(value = "account-to-transactions")
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
