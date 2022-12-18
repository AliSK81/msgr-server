package com.msgrserver.handler;

import com.msgrserver.service.PublicChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatHandler {
    private final PublicChatService publicChatService;

}
