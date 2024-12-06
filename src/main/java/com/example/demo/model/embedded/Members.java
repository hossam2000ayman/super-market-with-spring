package com.example.demo.model.embedded;

import com.example.demo.model.enums.Type;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Setter
@Getter
public class Members {
    String name;
    @Enumerated(EnumType.STRING)
    Type type;
    int age;
}
