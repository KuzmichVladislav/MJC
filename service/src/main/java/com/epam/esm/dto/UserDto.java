package com.epam.esm.dto;

import com.epam.esm.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.Set;

/**
 * DTO Class UserDto for user DTO object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends RepresentationModel<UserDto> {

    private long id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private boolean active;
    private Set<Role> roles;
}
