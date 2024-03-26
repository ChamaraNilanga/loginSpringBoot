package com.login.demo.service;

import com.login.demo.dto.UserDTO;
import com.login.demo.dto.requestDTO.AuthRequestDTO;
import com.login.demo.model.User;
import com.login.demo.repository.UserRepository;
import com.login.demo.util.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service

public class AuthServcice {
    private static final Logger logger = LoggerFactory.getLogger(AuthServcice.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTService jwtService;

    public CommonResponse loginUser (AuthRequestDTO authRequestDTO) {
        CommonResponse commonResponse = new CommonResponse();
        Optional<User> email = userRepository.findByEmail(authRequestDTO.getEmail());
        if(email==null){
            commonResponse.setMessage("Email Not Exists");
        }else{
            var isMatch = this.passwordEncoder.matches(authRequestDTO.getPassword(), email.get().getPassword());
            if(isMatch){
                var token = jwtService.generateJWTToken(authRequestDTO);
                Map<String, String> tokenMap = new HashMap<>();
                tokenMap.put("token", token);
                List<Object> payLoad = new ArrayList<>();
                payLoad.add(tokenMap);
                commonResponse.setPayload(payLoad);
                commonResponse.setMessage("User Logged in Successfully");
                commonResponse.setStatus(true);
            }else{
                commonResponse.setMessage("Invalid Credentials");
            }
        }
        return commonResponse;
    }
}
