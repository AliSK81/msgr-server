package com.msgrserver.model.dto.user;

import com.msgrserver.model.dto.ActionDto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserSignUpRequestDto extends ActionDto {
    String name;
    String username;
    String password;
    String avatar;
}
