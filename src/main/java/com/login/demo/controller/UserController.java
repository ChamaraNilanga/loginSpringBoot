package com.login.demo.controller;

import com.login.demo.dto.UserDTO;
import com.login.demo.service.UserService;
import com.login.demo.util.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public  String getUsers () {
        return "Users fetched Successfully";
    }

    @GetMapping("/welcome")
    public  String Welcome () {
        return "Users Welcome";
    }

    @GetMapping("/getOne")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public  String userOnly () {
        return "Users Only Welcome";
    }
}
