package com.msgrserver.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Action {
    private ActionType actionType;
    private ActionDto actionDto;

}
