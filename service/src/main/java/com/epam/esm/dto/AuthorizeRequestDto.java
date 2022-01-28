package com.epam.esm.dto;

import lombok.Data;

/**
 * The Class AuthorizeRequestDto contains parameters for authorization
 */
@Data
public class AuthorizeRequestDto {

    private String username;
    private String password;
}
