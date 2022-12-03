package com.msgrserver.model.entity.message;

import com.msgrserver.model.entity.chat.Chat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Message {
    @Id
    private Long id;
    private Long senderId;
    private LocalDateTime dateTime;
    private MessageType messageType;

}
