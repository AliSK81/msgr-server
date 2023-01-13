package com.msgrserver.model.dto.user;

import com.msgrserver.model.dto.ActionDto;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto extends ActionDto {
    private Long id;
    private String name;
    private String avatar;
    private String username;
    private String email;
}
