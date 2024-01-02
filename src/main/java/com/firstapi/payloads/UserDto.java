package com.firstapi.payloads;

import jakarta.validation.constraints.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Getter
@Setter
public class UserDto {

    private int id;
    @NotEmpty
    @Size(min=4, message = "UserName must be minimum of 4 character")
    private String name;
    @Email(message = "Invalid email address")
    private String email;
    @NotEmpty
    @Size(min=6 ,max = 12, message = "Password must me minimum of 6 character and max of 12")

    private String password;
    @NotEmpty
    private String about;

    private Set<RoleDto> roles = new HashSet<>();

}
