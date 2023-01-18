package com.msgrserver.service.chat;

import com.msgrserver.model.entity.chat.PrivateChat;

import java.util.Optional;

public interface PrivateChatService {

    PrivateChat findPrivateChat(Long chatId);

    PrivateChat createPrivateChat(Long user1Id, Long user2Id);

    Optional<PrivateChat> findPrivateChat(Long user1Id, Long user2Id);
}
