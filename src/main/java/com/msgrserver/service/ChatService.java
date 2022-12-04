package com.msgrserver.service;

import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.message.BinaryMessage;
import com.msgrserver.model.entity.message.Message;
import com.msgrserver.model.entity.message.TextMessage;
import com.msgrserver.model.entity.user.User;

import java.util.Set;

public interface ChatService {

    Chat addChat(Long userId, Chat chat);

    void deleteChat(Long userId, Long chatId);

    PublicChat joinPublicChat(Long chatId, Long userId);

    PublicChat leavePublicChat(Long chatId, Long userId);


}
