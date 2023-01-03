package com.msgrserver.model.entity.user;

import com.msgrserver.model.entity.chat.Chat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @ManyToMany(fetch = FetchType.LAZY)
    Set<Chat> chats;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String phone;
    private String email;
    private String username;
    private String password;
    private String name;
    private String avatar;
}
