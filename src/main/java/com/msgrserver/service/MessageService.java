package com.msgrserver.service;

import com.msgrserver.model.entity.TextMessage;

public interface MessageService {
    void sendText(TextMessage textMessage);
}
