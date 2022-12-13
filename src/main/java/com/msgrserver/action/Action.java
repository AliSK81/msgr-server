package com.msgrserver.action;

import com.msgrserver.model.dto.ActionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Action {
    private ActionType type;
    private ActionDto dto;
    private String dtoType;
    public Action(ActionType type, ActionDto dto) {
        this.type = type;
        this.dto = dto;
        dtoType = dto.getClass().getSimpleName();
    }
}

