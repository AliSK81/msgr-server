package com.msgrserver.service;

import com.msgrserver.model.entity.chat.PublicChat;

public interface ChatService {

    public PublicChat findChat(Long chatId);

    PublicChat savePublicChat(Long userId, PublicChat chat);

    void deletePublicChat(Long userId, Long chatId);

    PublicChat joinPublicChat(Long chatId, Long userId);

    PublicChat leavePublicChat(Long chatId, Long userId);

}
