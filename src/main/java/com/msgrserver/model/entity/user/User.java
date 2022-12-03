package com.msgrserver.model.entity.user;

import com.msgrserver.model.entity.chat.Chat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {
    @Id
    private Long id;
    private String phone;
    private String email;

    @ManyToMany
    private Set<Chat> chats;

}
