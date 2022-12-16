package com.msgrserver.action;

import com.msgrserver.model.dto.ActionDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Action {
    private ActionType type;
    private ActionDto dto;
    private String dtoName;

    public static class ActionBuilder {
        public ActionBuilder dto(ActionDto dto) {
            this.dto = dto;
            this.dtoName = dto.getClass().getSimpleName();
            return this;
        }
    }

}

