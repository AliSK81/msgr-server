package com.msgrserver.service;

import com.msgrserver.model.entity.chat.PrivateChat;

public interface PrivateChatService {
    PrivateChat createPrivateChat(Long user1Id, Long user2Id);
}
