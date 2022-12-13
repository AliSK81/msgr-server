package com.msgrserver.model.entity.chat;

import com.msgrserver.model.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
public class PublicChat extends Chat {

    private String title;
    private String link;

    @ManyToOne
    private User owner;

    @ManyToMany
    private Set<User> members;

    @ManyToMany
    private Set<User> admins;
}
