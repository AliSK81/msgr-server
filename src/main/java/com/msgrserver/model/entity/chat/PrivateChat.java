package com.msgrserver.model.entity.chat;

import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.model.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PrivateChat that = (PrivateChat) o;
        return Objects.equals(user1, that.user1) && Objects.equals(user2, that.user2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), user1, user2);
    }
}
