package com.msgrserver.action;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
    private String token;
}

