//package com.epam.esm.controller;
//
//import com.epam.esm.dto.UserDto;
//import com.epam.esm.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
//@RequiredArgsConstructor
//public class RegistrationController {
//
//    private final UserService userService;
//
//    @PostMapping("/registration")
//    public UserDto addUser(@RequestBody UserDto userDto) {
//        System.out.println("yes");
//        return userService.add(userDto);
//    }
//}
