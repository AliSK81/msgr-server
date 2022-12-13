package com.msgrserver.handler;

import com.msgrserver.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatHandler {
    private final ChatService chatService;

}
