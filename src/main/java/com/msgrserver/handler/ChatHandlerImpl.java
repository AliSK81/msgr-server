package com.msgrserver.handler;

import com.msgrserver.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatHandlerImpl {
    private final ChatService chatService;

}
