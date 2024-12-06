package com.example.demo.model.embedded;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class Address {
    String street;
    String city;
    String country;
    String state;
    String zipCode;

}
