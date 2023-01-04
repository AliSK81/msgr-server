package com.msgrserver.model.entity.message;

import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class BinaryMessage extends Message {
    private String name;
    private String caption;
    private Byte[] data;
}
