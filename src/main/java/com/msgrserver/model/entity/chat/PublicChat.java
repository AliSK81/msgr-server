package com.msgrserver.model.entity.chat;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PublicChat extends Chat {
    private String title;
    private Long ownerId;
    private String link;
}
