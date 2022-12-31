package com.msgrserver.model.entity.chat;

import com.msgrserver.model.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class PrivateChat extends Chat {
    @OneToOne
    private User user1;
    @OneToOne
    private User user2;

    public Long getReceiverId(Long senderId)
    {
        return user1.getId().equals(senderId) ? user2.getId() : user1.getId();
    }
}
