package com.msgrserver.model.entity.chat;

import com.msgrserver.model.entity.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
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
