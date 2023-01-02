package com.msgrserver.model.entity.chat;

import com.msgrserver.model.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Getter
public class PublicChat extends Chat {

    private String title;
    private String link;

    @ManyToOne
    private User owner;

    @ManyToMany
    private Set<User> members;

    @ManyToMany
    private Set<User> admins;

    public Set<Long> getId(Set<User> users) {
        Set<Long> usersId = new HashSet<>();
        users.forEach(user -> usersId.add(user.getId()));
        return usersId;
    }
}
