package com.login.demo.service;

import com.login.demo.dto.UserDTO;
import com.login.demo.model.User;
import com.login.demo.repository.UserRepository;
import com.login.demo.util.CommonResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;
    public CommonResponse register(UserDTO userDTO){
        CommonResponse commonResponse = new CommonResponse();
        User user = new User(
                userDTO.getName(),
                userDTO.getEmail(),
                this.passwordEncoder.encode(userDTO.getPassword()),
                userDTO.getPhoneNumber(),
                userDTO.getCity(),
                userDTO.getCountry(),
                userDTO.getRole()
        );
        Optional<User> email = userRepository.findByEmail(userDTO.getEmail());
        logger.info("this user : {}",email.isPresent());
        if(email.isEmpty()){
            userRepository.save(user);
            commonResponse.setPayload(Collections.singletonList(user));
            commonResponse.setStatus(true);
        }else{
            commonResponse.setPayload(null);
            List<String> errorMessages = new ArrayList<>();
            errorMessages.add("Email Already Exists");
            commonResponse.setErrorMessages(errorMessages);
            commonResponse.setStatus(true);
        }

        return commonResponse;
    }
}
