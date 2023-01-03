package com.msgrserver.model.entity.chat;

import com.msgrserver.exception.UserNotFoundException;
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

    public Long getReceiver(Long senderId) {
        return super.getUsers().stream()
                .filter(user -> !user.getId().equals(senderId))
                .findAny()
                .orElseThrow(UserNotFoundException::new)
                .getId();
    }
}
