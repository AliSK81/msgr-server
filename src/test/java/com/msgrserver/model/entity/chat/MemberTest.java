package com.msgrserver.model.entity.chat;

import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.ChatRepository;
import com.msgrserver.repository.MemberRepository;
import com.msgrserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
class MemberTest {

    private final UserRepository userRepository;
    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;

    @Test
    void builder() {

        var user = userRepository.save(User.builder().id(1L).build());
        var chat = chatRepository.save(PublicChat.builder().id(1L).build());

        var memberId = MemberId.builder().chatId(chat.getId()).userId(user.getId()).build();

        memberRepository.save(Member.builder().id(memberId).chat(chat).user(user).build());

    }
}