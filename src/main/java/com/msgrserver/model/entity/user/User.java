package com.msgrserver.model.entity.user;

import jakarta.persistence.*;
import lombok.*;

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
    @Lob
    @Column(name = "avatar", columnDefinition="BLOB")
    private Byte[] avatar;
    private Boolean accessAddPublicChat;
    private Boolean visibleAvatar;
}
