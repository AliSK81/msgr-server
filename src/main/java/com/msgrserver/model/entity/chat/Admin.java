package com.msgrserver.model.entity.chat;

import com.msgrserver.model.entity.user.User;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
    @EmbeddedId
    private AdminId id;

    @ManyToOne
    @MapsId(value = "userId")
    private User user;

    @ManyToOne
    @MapsId(value = "chatId")
    private PublicChat chat;
}
