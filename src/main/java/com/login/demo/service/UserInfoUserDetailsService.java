package com.login.demo.service;

import com.login.demo.controller.UserController;
import com.login.demo.model.User;
import com.login.demo.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LoggerFactory.getLogger(UserInfoUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername (String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        logger.error("user roles with : {}",userOptional);
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        logger.info("dddddd : {}",user);
        return new UserInfoUserDetails(user);
    }
}
