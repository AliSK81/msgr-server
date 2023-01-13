package com.msgrserver.action;

import com.msgrserver.model.entity.user.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ActionRequest {
    private Action action;
    private User user;
}
