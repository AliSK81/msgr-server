package com.msgrserver.service.chat;

import com.msgrserver.exception.BadRequestException;
import com.msgrserver.exception.ChatNotFoundException;
import com.msgrserver.exception.NotImplementedException;
import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.chat.Member;
import com.msgrserver.model.entity.chat.PrivateChat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.message.Message;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final MemberRepository memberRepository;
    private final PrivateChatRepository privateChatRepository;

    @Override
    public Chat findChat(Long chatId) {
        return chatRepository.findById(chatId).orElseThrow(ChatNotFoundException::new);
    }

    @Override
    public Set<Message> getChatMessages(Long userId, Long chatId) {

        Chat chat = findChat(chatId);

        boolean isMember;

        if(chat instanceof PrivateChat privateChat) {

            isMember = privateChat.getUser1().getId().equals(userId) || privateChat.getUser2().getId().equals(userId);

        } else if (chat instanceof PublicChat) {

            isMember = memberRepository.findByChatIdAndUserId(chatId, userId).isPresent();

        } else {
            throw new NotImplementedException();
        }

        if (!isMember)
            throw new BadRequestException();

        return messageRepository.findMessagesByChatId(chatId);
    }

    @Transactional
    @Override
    public void deleteChat(Long userId, Long chatId) {
        Chat chat = findChat(chatId);

        if (chat instanceof PrivateChat privateChat) {
            boolean isUserInChat =
                    List.of(privateChat.getUser1().getId(), privateChat.getUser2().getId()).contains(userId);

            if (!isUserInChat) {
                throw new BadRequestException();
            }

        } else if (chat instanceof PublicChat publicChat) {

            if (!publicChat.getOwner().getId().equals(userId)) {
                throw new BadRequestException();
            }

            adminRepository.deleteByIdChatId(chatId);
            memberRepository.deleteByChatId(chatId);
        }

        messageRepository.deleteByChatId(chatId);
        chatRepository.deleteById(chatId);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
    }
}

