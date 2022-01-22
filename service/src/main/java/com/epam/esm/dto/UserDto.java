package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

import static com.epam.esm.exception.ExceptionKey.USER_FIRST_NAME_IS_NOT_VALID;
import static com.epam.esm.exception.ExceptionKey.USER_FIRST_NAME_LENGTH_IS_NOT_VALID;
import static com.epam.esm.exception.ExceptionKey.USER_LAST_NAME_IS_NOT_VALID;
import static com.epam.esm.exception.ExceptionKey.USER_LAST_NAME_LENGTH_IS_NOT_VALID;
import static com.epam.esm.exception.ExceptionKey.USER_USERNAME_IS_NOT_VALID;
import static com.epam.esm.exception.ExceptionKey.USER_USERNAME_LENGTH_IS_NOT_VALID;

/**
 * DTO Class UserDto for user DTO object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends RepresentationModel<UserDto> {

    private long id;
    @Size(min = 2, max = 16, message = USER_USERNAME_LENGTH_IS_NOT_VALID)
    @Pattern(regexp = "^[\\w_]{2,16}$", message = USER_USERNAME_IS_NOT_VALID)
    private String username;
    @Size(min = 2, max = 50, message = USER_FIRST_NAME_LENGTH_IS_NOT_VALID)
    @Pattern(regexp = "^[\\w]{2,50}$", message = USER_FIRST_NAME_IS_NOT_VALID)
    private String firstName;
    @Size(min = 2, max = 50, message = USER_LAST_NAME_LENGTH_IS_NOT_VALID)
    @Pattern(regexp = "^[\\w]{2,50}$", message = USER_LAST_NAME_IS_NOT_VALID)
    private String lastName;
    private Set<RoleDto> roles;
}
