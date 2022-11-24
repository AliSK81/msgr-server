package com.msgrserver.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    private Long id;
    private String phone;
}
