package com.msgrserver.action;

import com.msgrserver.model.dto.ActionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Action {
    private ActionType type;
    private ActionDto dto;
}

