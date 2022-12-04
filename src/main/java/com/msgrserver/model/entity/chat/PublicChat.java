package com.msgrserver.model.entity.chat;

import com.msgrserver.model.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
public class PublicChat extends Chat {
    private String title;
    private String link;

    @ManyToMany
    private Set<User> members;

    @ManyToMany
    private Set<User> admins;
}
