package com.epam.esm.controller;

import lombok.Data;

@Data
public class AuthorizeRequest {

    private String login;
    private String password;
}
