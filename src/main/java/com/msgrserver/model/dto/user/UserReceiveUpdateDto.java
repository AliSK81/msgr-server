package com.msgrserver.model.dto.user;

import com.msgrserver.model.dto.ActionDto;
import com.msgrserver.model.dto.chat.ChatReceiveProfileUpdateDto;
import lombok.*;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserReceiveUpdateDto extends ActionDto {

    Set<ChatReceiveProfileUpdateDto> chat;
}
