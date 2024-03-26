package com.login.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class UserDTO {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String city;
    private String country;
    private String role;
}
