package com.msgrserver.service;

import com.msgrserver.model.entity.chat.PublicChat;

public interface PublicChatService {

    public PublicChat findPublicChat(Long chatId);

    PublicChat savePublicChat(Long userId, PublicChat chat);

    void deletePublicChat(Long userId, Long chatId);

    PublicChat joinPublicChat(Long chatId, Long userId);

    PublicChat leavePublicChat(Long chatId, Long userId);

}
