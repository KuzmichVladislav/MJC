package com.epam.esm.dto;

import lombok.Data;

@Data
public class AuthorizeRequestDto {

    private String username;
    private String password;
}
