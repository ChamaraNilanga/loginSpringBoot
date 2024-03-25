package com.login.demo.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class CommonResponse {
    private List<Object> payload = null;
    private List<String> errorMessages = new ArrayList<>();
    private boolean status = false;
    private String message = null;
}
