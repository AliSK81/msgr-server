package com.msgrserver.model.entity.chat;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberId implements Serializable {
    private Long chatId;
    private Long userId;
}
