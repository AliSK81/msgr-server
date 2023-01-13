package com.msgrserver.model.dto.user;

import com.msgrserver.model.dto.ActionDto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserDto extends ActionDto {
    private Long id;
    private String name;
    private String avatar;
    private String username;
    private String email;
}
