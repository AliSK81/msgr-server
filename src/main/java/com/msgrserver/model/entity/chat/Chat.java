package com.msgrserver.model.entity.chat;

import com.msgrserver.model.entity.user.User;
import com.msgrserver.model.entity.message.Message;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@ToString
public class Chat {
    @Id
    private Long id;


    @OneToMany
    private Set<Message> messages;
}
