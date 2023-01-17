package com.msgrserver.model.entity.message;

import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long date;
    private MessageType messageType;

    @ManyToOne
    private User sender;

    @ManyToOne
    private Chat chat;
}
