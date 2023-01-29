package com.msgrserver.action.handler;

import com.msgrserver.action.ActionType;
import com.msgrserver.model.dto.ActionDto;

public interface ActionHandlerFactory {

    ActionHandler<ActionDto> getHandler(ActionType actionType);

}
