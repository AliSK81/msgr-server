package com.msgrserver.handler;

import com.msgrserver.action.Action;
import com.msgrserver.action.ActionRequest;
import com.msgrserver.action.ActionResult;
import com.msgrserver.exception.NotImplementedException;
import com.msgrserver.handler.chat.ChatHandler;
import com.msgrserver.handler.chat.PublicChatHandler;
import com.msgrserver.handler.message.MessageHandler;
import com.msgrserver.handler.user.UserHandler;
import com.msgrserver.model.dto.chat.*;
import com.msgrserver.model.dto.message.MessageDeleteRequestDto;
import com.msgrserver.model.dto.message.MessageSendTextRequestDto;
import com.msgrserver.model.dto.user.UserEditProfileRequestDto;
import com.msgrserver.model.dto.user.UserSignInRequestDto;
import com.msgrserver.model.dto.user.UserSignUpRequestDto;
import com.msgrserver.model.dto.user.UserViewProfileRequestDto;
import com.msgrserver.model.entity.user.UserSession;
import com.msgrserver.service.user.SessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActionHandlerImpl implements ActionHandler {

    private final MessageHandler messageHandler;
    private final UserHandler userHandler;
    private final PublicChatHandler publicChatHandler;
    private final SessionService sessionService;
    private final ChatHandler chatHandler;

    public ActionResult handle(ActionRequest request) {

        Action action = request.getAction();

        switch (action.getType()) {
            case SIGN_UP -> {
                return userHandler.signUp((UserSignUpRequestDto) action.getDto());
            }
            case SIGN_IN -> {
                return userHandler.signIn((UserSignInRequestDto) action.getDto());
            }
            default -> {
                ActionResult actionResult;
                UserSession session = sessionService.findUserSession(action.getToken());
                Long userId = session.getUser().getId();

                switch (action.getType()) {

                    case SEND_TEXT ->
                            actionResult = messageHandler.sendText(userId, (MessageSendTextRequestDto) action.getDto());

                    case GET_USER_CHATS -> actionResult = userHandler.getUserChats(userId);

                    case VIEW_USER_PROFILE ->
                            actionResult = userHandler.getUserProfile(userId, (UserViewProfileRequestDto) action.getDto());

                    case JOIN_CHAT_WITH_LINK ->
                            actionResult = publicChatHandler.joinChatWithLink(userId, (PublicChatJoinWithLinkRequestDto) action.getDto());

                    case ADD_USER_BY_ADMIN ->
                            actionResult = publicChatHandler.addUserToPublicChat(userId, (PublicChatAddUserRequestDto) action.getDto());

                    case DELETE_USER_BY_ADMIN ->
                            actionResult = publicChatHandler.deleteUserFromPublicChat(userId, (PublicChatDeleteUserRequestDto) action.getDto());

                    case EDIT_PROFILE ->
                            actionResult = userHandler.editProfile(userId, (UserEditProfileRequestDto) action.getDto());

                    case CREATE_PUBLIC_CHAT ->
                            actionResult = publicChatHandler.createPublicChat(userId, (PublicChatCreateRequestDto) action.getDto());

                    case GET_CHAT_MESSAGES ->
                            actionResult = chatHandler.getChatMessages(userId, (ChatGetMessagesRequestDto) action.getDto());

                    case DELETE_CHAT ->
                            actionResult = chatHandler.deleteChat(userId, (ChatDeleteRequestDto) action.getDto());

                    case DELETE_MESSAGE ->
                            actionResult = messageHandler.deleteMessage(userId, (MessageDeleteRequestDto) action.getDto());

                    case DELETE_ADMIN_PUBLIC_CHAT ->
                            actionResult = publicChatHandler.deleteAdminPublicChat(userId, (PublicChatDeleteAdminRequestDto) action.getDto());

                    case SELECT_NEW_ADMIN_PUBLIC_CHAT ->
                            actionResult = publicChatHandler.selectNewAdminPublicChat(userId, (PublicChatSelectNewAdminRequestDto) action.getDto());

                    case EDIT_PROFILE_PUBLIC_CHAT ->
                            actionResult = publicChatHandler.editProfilePublicChat(userId, (PublicChatEditProfileRequestDto) action.getDto());

                    case LEAVE_PUBLIC_CHAT ->
                            actionResult = publicChatHandler.leavePublicChat(userId, (PublicChatLeaveRequestDto) action.getDto());

                    case GET_PUBLIC_CHAT_MEMBERS ->
                        actionResult = publicChatHandler.getPublicChatMembers(userId, (PublicChatGetMembersRequestDto) action.getDto());

                    default -> throw new NotImplementedException();
                }

                actionResult.setUser(session.getUser());
                return actionResult;
            }
        }
    }
}
