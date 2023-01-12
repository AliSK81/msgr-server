package com.msgrserver.service.chat;

import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.PrivateChatRepository;
import com.msgrserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PrivateChatServiceImpl implements PrivateChatService {
    private final PrivateChatRepository privateChatRepository;
    private final UserRepository userRepository;

    @Override
    public PrivateChat createPrivateChat(Long user1Id, Long user2Id) {
        User user1 = findUser(user1Id);
        User user2 = findUser(user2Id);

        PrivateChat chat = PrivateChat.builder().users(Set.of(user1, user2)).build();

        return privateChatRepository.save(chat);
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }
}
