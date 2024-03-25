package com.login.demo.controller;

import com.login.demo.dto.UserDTO;
import com.login.demo.dto.requestDTO.AuthRequestDTO;
import com.login.demo.service.AuthServcice;
import com.login.demo.util.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/login/v1")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private AuthServcice authServcice;

    @Autowired
    public AuthController(AuthServcice authServcice){
        this.authServcice = authServcice;
    }

    @PostMapping
    public CommonResponse loginUser (@RequestBody AuthRequestDTO authRequestDTO){
        logger.info("Login Route");
        return authServcice.loginUser(authRequestDTO);
    }

}
