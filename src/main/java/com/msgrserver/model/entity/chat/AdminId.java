package com.msgrserver.model.entity.chat;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminId implements Serializable {
    private Long userId;
    private Long chatId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdminId adminId)) return false;
        return Objects.equals(userId, adminId.userId) && Objects.equals(chatId, adminId.chatId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, chatId);
    }
}
