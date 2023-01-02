package com.msgrserver.model.entity.chat;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.msgrserver.model.entity.message.Message;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.swing.plaf.BorderUIResource;
import java.util.Set;

@Entity
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Getter
@Setter
@ToString
@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes(value = {
        @JsonSubTypes.Type(value = PrivateChat.class),
        @JsonSubTypes.Type(value = PublicChat.class)}
)
public class Chat {
    @Id
    private Long id;

    private ChatType type;

    @OneToMany
    private Set<Message> messages;
}
