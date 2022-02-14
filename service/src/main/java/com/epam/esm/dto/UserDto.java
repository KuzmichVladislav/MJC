package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

import static com.epam.esm.exception.ExceptionKey.*;

/**
 * DTO Class UserDto for user DTO object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends RepresentationModel<UserDto> {

    private long id;
    @NotNull(message = USER_USERNAME_MIGHT_NOT_BE_NULL)
    @Size(min = 2, max = 16, message = USER_USERNAME_LENGTH_IS_NOT_VALID)
    @Pattern(regexp = "^[\\w_]{2,16}$", message = USER_USERNAME_IS_NOT_VALID)
    private String username;
    @NotNull(message = USER_FIRST_NAME_MIGHT_NOT_BE_NULL)
    @Size(min = 2, max = 50, message = USER_FIRST_NAME_LENGTH_IS_NOT_VALID)
    @Pattern(regexp = "^[\\w]{2,50}$", message = USER_FIRST_NAME_IS_NOT_VALID)
    private String firstName;
    @NotNull(message = USER_LAST_NAME_MIGHT_NOT_BE_NULL)
    @Size(min = 2, max = 50, message = USER_LAST_NAME_LENGTH_IS_NOT_VALID)
    @Pattern(regexp = "^[\\w]{2,50}$", message = USER_LAST_NAME_IS_NOT_VALID)
    private String lastName;
    private Set<RoleDto> roles;
}
