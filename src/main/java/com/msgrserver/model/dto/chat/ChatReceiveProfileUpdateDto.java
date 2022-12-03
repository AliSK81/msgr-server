package com.msgrserver.model.dto.chat;

import com.msgrserver.model.entity.message.Message;

public class ChatReceiveProfileUpdateDto {
    private Integer chatId;
    private String name;
    private String imageUrl;
    private String chatLink;
    private String bio;
    private Message lastMessage;
}
