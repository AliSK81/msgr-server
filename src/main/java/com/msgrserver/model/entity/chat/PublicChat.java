package com.msgrserver.model.entity.chat;

import com.msgrserver.model.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

    @ManyToOne
    private User owner;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> admins;
}
