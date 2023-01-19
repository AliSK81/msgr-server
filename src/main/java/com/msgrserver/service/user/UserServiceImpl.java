package com.msgrserver.service.user;

import com.msgrserver.exception.InvalidPasswordException;
import com.msgrserver.exception.UserNotFoundException;
import com.msgrserver.exception.UsernameAlreadyTakenException;
import com.msgrserver.model.entity.chat.Chat;
import com.msgrserver.model.entity.chat.PublicChat;
import com.msgrserver.model.entity.user.User;
import com.msgrserver.repository.PrivateChatRepository;
import com.msgrserver.repository.PublicChatRepository;
import com.msgrserver.repository.UserRepository;
import com.msgrserver.util.Hashing;
import com.msgrserver.util.PasswordChecker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PrivateChatRepository privateChatRepository;
    private final PublicChatRepository publicChatRepository;

    @Override
    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User saveUser(User user) {
        checkUniqueUsername(user.getUsername());
//        checkStrongPassword(user.getPassword());
        hashPassword(user);
        user.setAllowedInvite(true);
        user.setVisibleAvatar(true);
        return userRepository.save(user);
    }

    private void hashPassword(User user) {
        
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public Set<Chat> getUserChats(Long userId) {
        Set<PublicChat> publicChats = new HashSet<>(publicChatRepository.findPublicChatsByMembersId(userId));
        Set<Chat> chats = new HashSet<>(publicChats);
        chats.addAll(privateChatRepository.findPrivateChatsByUser1Id(userId));
        chats.addAll(privateChatRepository.findPrivateChatsByUser2Id(userId));
        return chats;
    }

    @Override
    public User findUser(String username, String password) {
         var user = userRepository.findUserByUsername(username).orElseThrow(UserNotFoundException::new);
        try {
            var isValid = Hashing.validatePassword(password, user.getPassword());
            if (!isValid) throw new UserNotFoundException();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new UserNotFoundException();
        }
        return user;
    }

    @Override
    public User findUser(String username) {
        return userRepository.findUserByUsername(username)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User editProfile(User userInput, Long userId) {
        User user = findUser(userId);
        user.setId(userInput.getId());
        user.setName(userInput.getName());
        user.setAvatar(userInput.getAvatar());
        user.setUsername(userInput.getUsername());
        user.setEmail(userInput.getEmail());
        user.setAllowedInvite(user.getAllowedInvite());
        user.setVisibleAvatar(user.getVisibleAvatar());
        return userRepository.save(user);
    }

    private void checkUniqueUsername(String username) {
        boolean userExist = userRepository.findUserByUsername(username).isPresent();
        if (userExist) {
            throw new UsernameAlreadyTakenException();
        }
    }

    private void checkStrongPassword(String password) {
        if (!PasswordChecker.isStrong(password)) {
            throw new InvalidPasswordException();
        }
    }
}