package com.msgrserver.model.entity.chat;

import com.msgrserver.model.entity.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Entity
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long createdAt;

    private ChatType type;
}
