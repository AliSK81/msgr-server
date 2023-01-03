package com.msgrserver.model.entity.chat;

import com.msgrserver.model.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PrivateChat extends Chat {
    @OneToOne
    private User user1;
    @OneToOne
    private User user2;

    public Long getReceiverId(Long senderId) {
        return user1.getId().equals(senderId) ? user2.getId() : user1.getId();
    }
}
