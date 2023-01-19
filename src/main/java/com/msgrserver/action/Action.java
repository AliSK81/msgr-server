package com.msgrserver.action;

import com.msgrserver.model.dto.ActionDto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Action {
    private ActionType type;
    private ActionDto dto;
    private String token;
    private String error;
}

