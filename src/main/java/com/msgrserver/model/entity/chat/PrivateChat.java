package com.msgrserver.model.entity.chat;

import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.model.entity.user.User;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class PrivateChat extends Chat {

    public User getParticipant(Long userId) {
        return super.getUsers().stream()
                .filter(u -> !u.getId().equals(userId))
                .findAny()
                .orElseThrow(UserNotFoundException::new);
    }
}
