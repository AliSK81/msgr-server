package com.msgrserver.action;

import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Builder
@Getter
public class Response {
    private Action action;
    private Set<Long> receivers;
}
