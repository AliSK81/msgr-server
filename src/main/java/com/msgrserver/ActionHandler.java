package com.msgrserver;

import com.msgrserver.model.entity.TextMessage;
import com.msgrserver.model.entity.User;
import com.msgrserver.model.dto.Action;
import com.msgrserver.service.MessageService;
import com.msgrserver.service.UserService;
import com.msgrserver.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;

@AllArgsConstructor
@Controller
public class ActionHandler {
    private UserService userService;
    private MessageService messageService;

    public void handleAction(Action action) {

        switch (action.getActionType()) {

            case SIGN_UP -> userService.addUser(
                    Mapper.map(action.getActionDto(), User.class)
            );

            case SEND_TEXT -> messageService.sendText(
                    Mapper.map(action.getActionDto(), TextMessage.class)
            );

        }

    }

}
