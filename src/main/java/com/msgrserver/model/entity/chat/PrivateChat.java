package com.msgrserver.model.entity.chat;

import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.model.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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

    @ManyToOne
    private User user1;

    @ManyToOne
    private User user2;

    public User getParticipant(Long userId) {
        return user1.getId().equals(userId) ? user2 : user1;
    }
}
