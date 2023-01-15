package com.msgrserver.action;

import com.msgrserver.model.entity.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Builder
@Getter
public class ActionResult {
    @Setter
    private User user;
    private Action action;
    private Set<Long> receivers;
}