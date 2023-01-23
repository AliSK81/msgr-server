package com.msgrserver.model.entity.chat;

import com.msgrserver.model.entity.user.User;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn
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

    @ManyToMany
    @JoinTable(
            name = "member",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> members;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PublicChat chat = (PublicChat) o;
        return Objects.equals(title, chat.title) && Objects.equals(link, chat.link) && Objects.equals(avatar, chat.avatar) && Objects.equals(owner, chat.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), title, link, avatar, owner);
    }
}
