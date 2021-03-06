package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import static com.epam.esm.exception.ExceptionKey.USER_PASSWORD_IS_NOT_VALID;
import static com.epam.esm.exception.ExceptionKey.USER_PASSWORD_LENGTH_IS_NOT_VALID;
import static com.epam.esm.exception.ExceptionKey.USER_PASSWORD_MIGHT_NOT_BE_NULL;

/**
 * The class UserRegistrationDto extends the user class to set a password
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationDto extends UserDto {

    @NotNull(message = USER_PASSWORD_MIGHT_NOT_BE_NULL)
    @Size(min = 8, max = 50, message = USER_PASSWORD_LENGTH_IS_NOT_VALID)
    @Pattern(regexp = "^[\\w_]{8,50}$", message = USER_PASSWORD_IS_NOT_VALID)
    private String password;
}
