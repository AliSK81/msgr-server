package com.msgrserver.model.entity.chat;

import com.msgrserver.model.entity.message.Message;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Set;

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

    private ChatType type;

    @OneToMany
    private Set<Message> messages;
}
