package com.msgrserver.model.entity.message;

import com.msgrserver.model.entity.chat.Chat;
import jakarta.persistence.*;
import lombok.*;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long senderId;
    private LocalDateTime dateTime;
    private MessageType messageType;

    @ManyToOne
    private Chat chat;
}
