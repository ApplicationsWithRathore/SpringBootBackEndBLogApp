package com.firstapi.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.firstapi.entity.Comment;
import com.firstapi.entity.Post;
import com.firstapi.entity.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
