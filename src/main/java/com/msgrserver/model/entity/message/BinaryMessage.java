package com.msgrserver.model.entity.message;

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
public class BinaryMessage extends Message {
    private String name;
    private String caption;
}
