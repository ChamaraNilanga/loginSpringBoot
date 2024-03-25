package com.login.demo.controller;

import com.login.demo.dto.UserDTO;
import com.login.demo.service.UserService;
import com.login.demo.util.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/user/v1")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserService userService;

    @Autowired
    public UserController (UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public CommonResponse registerUser (@RequestBody UserDTO userDTO){
        logger.error("Company created with id");
        return userService.register(userDTO);
    }
}
