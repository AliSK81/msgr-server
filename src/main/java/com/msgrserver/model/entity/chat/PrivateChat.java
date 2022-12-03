package com.msgrserver.model.entity.chat;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PrivateChat extends Chat {
    private String username;
    private String firstName;
    private String lastName;
}
