package com.msgrserver.model.entity.chat;

import com.msgrserver.model.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PublicChat extends Chat {
    private String title;
    private String link;

    private String avatar;

    @ManyToOne
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "admin",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> admins;

    public Set<Long> getId(Set<User> users) {
        Set<Long> usersId = new HashSet<>();
        users.forEach(user -> usersId.add(user.getId()));
        return usersId;
    }
}
