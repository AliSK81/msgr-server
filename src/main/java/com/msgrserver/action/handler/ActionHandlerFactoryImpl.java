package com.msgrserver.action.handler;

import com.msgrserver.action.ActionType;
import com.msgrserver.exception.NotImplementedException;
import com.msgrserver.model.dto.ActionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class ActionHandlerFactoryImpl implements ActionHandlerFactory {
    private final Set<ActionHandler<? extends ActionDto>> handlers;

    @Override
    @SuppressWarnings("unchecked")
    public ActionHandler<ActionDto> getHandler(ActionType actionType) {
        return handlers.stream()
                .filter(handler -> handler.type().equals(actionType))
                .findFirst()
                .map(handler -> (ActionHandler<ActionDto>) handler)
                .orElseThrow(NotImplementedException::new);
    }
}
