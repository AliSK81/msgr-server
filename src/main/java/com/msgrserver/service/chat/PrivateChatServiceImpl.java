package com.msgrserver.service.chat;

import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.model.entity.chat.ChatType;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.PrivateChatRepository;
import com.msgrserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrivateChatServiceImpl implements PrivateChatService {
    private final PrivateChatRepository privateChatRepository;
    private final UserRepository userRepository;

    @Override
    public PrivateChat createPrivateChat(Long user1Id, Long user2Id) {
        User user1 = findUser(user1Id);
        User user2 = findUser(user2Id);

        PrivateChat chat = PrivateChat.builder()
                .user1(user1)
                .user2(user2)
                .type(ChatType.PRIVATE)
                .build();

        return privateChatRepository.save(chat);
    }

    @Override
    public Optional<PrivateChat> findPrivateChat(Long user1Id, Long user2Id) {
        return privateChatRepository.findPrivateChatByUsersId(user1Id, user2Id);
//                .orElseThrow(ChatNotFoundException::new);
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}
