package com.msgrserver.model.entity.user;

import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.message.Message;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String phone;
    private String email;
    private String username;
    private String password;
    private String name;
    private String avatar;

    @OneToMany(mappedBy = "sender")
    private Set<Message> messages;

    @ManyToMany(mappedBy = "users")
    private Set<Chat> chats;
}
