package com.msgrserver.model.dto.chat;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.entity.message.Message;

public class ChatReceiveProfileUpdateDto extends ActionDto {
    private Integer chatId;
    private String name;
    private String imageUrl;
    private String chatLink;
    private String bio;
    private Message lastMessage;
}
