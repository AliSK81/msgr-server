package com.msgrserver.model.entity.message;

import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Message {
    @Id
    @GeneratedValue
    private Long id;
    private LocalDateTime dateTime;
    private MessageType messageType;

    @ManyToOne
    private User sender;

    @ManyToOne
    private Chat chat;
}
