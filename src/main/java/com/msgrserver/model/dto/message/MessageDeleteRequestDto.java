package com.msgrserver.model.dto.message;

import com.msgrserver.model.dto.ActionDto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MessageDeleteRequestDto extends ActionDto {
    Long messageId;
}
