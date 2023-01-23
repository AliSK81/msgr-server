package com.msgrserver.action.handler;

import com.msgrserver.action.ActionResult;
import com.msgrserver.action.ActionType;
import com.msgrserver.model.dto.ActionDto;

public interface ActionHandler<T extends ActionDto> {

    ActionType type();

    ActionResult handle(Long userId, T dto);

}
