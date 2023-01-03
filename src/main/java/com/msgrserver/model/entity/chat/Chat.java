package com.msgrserver.model.entity.chat;

import com.msgrserver.model.entity.message.Message;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Chat {
    @Id
    private Long id;

    private ChatType type;

    @OneToMany
    private Set<Message> messages;
}
