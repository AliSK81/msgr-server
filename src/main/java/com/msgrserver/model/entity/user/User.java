package com.msgrserver.model.entity.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String phone;
    private String email;
    private String username;
    private String password;
    private String name;
    private String avatar;
    private boolean allowedAddGroup;
    private boolean visiblePhone;
    private boolean visibleAvatar;
}
