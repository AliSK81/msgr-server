package com.msgrserver.service;

import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.ChatRepository;
import com.msgrserver.repository.PrivateChatRepository;
import com.msgrserver.repository.PublicChatRepository;
import com.msgrserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class PublicChatServiceTest {

    private final UserService userService;
    private final PublicChatService publicChatService;
    private final MessageService messageService;

    private final UserRepository userRepository;
    private final PublicChatRepository publicChatRepository;
    private final PrivateChatRepository privateChatRepository;

    private final ChatRepository chatRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    public void joinPublicChatTest() {

        User user = User.builder().id(1L).name("user1").build();
        User user2 = User.builder().id(2L).name("user2").build();
        PublicChat chat = PublicChat.builder().id(1L).owner(user).title("gap1").build();
        PublicChat chat2 = PublicChat.builder().id(2L).owner(user2).title("gap2").build();


        userRepository.save(user);
        userRepository.save(user2);

        chat.setAdmins(Set.of(user, user2));
        chatRepository.save(chat);

        System.out.println(userRepository.findAdminsByChatId(chat.getId()));

//        PrivateChat chat3 = PrivateChat.builder().id(3L).users(Set.of(user, user2)).build();
//        chatRepository.save(chat3);


//        userRepository.save(user);
//        userRepository.save(user2);
//        chatRepository.save(chat3);
//        System.out.println(privateChatRepository.findPrivateChatsByUsersId(2L));
//        System.out.println(chatRepository.findChatsByUsersId(2L));
    }

}