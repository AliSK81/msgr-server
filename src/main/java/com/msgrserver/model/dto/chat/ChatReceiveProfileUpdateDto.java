package com.msgrserver.model.dto.chat;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.entity.message.Message;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ChatReceiveProfileUpdateDto extends ActionDto {
    Integer chatId;
    String name;
    String imageUrl;
    String chatLink;
    String bio;
    Message lastMessage;
}
