package com.msgrserver.model.entity.message;

import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
    @Lob
    private Byte[] data;
}
