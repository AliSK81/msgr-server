package com.msgrserver.model.dto.chat;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.entity.message.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatReceiveProfileUpdateDto extends ActionDto {
    private Integer chatId;
    private String name;
    private String imageUrl;
    private String chatLink;
    private String bio;
    private Message lastMessage;
}
