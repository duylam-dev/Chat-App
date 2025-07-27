package com.hust.chatappbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "Email must be not null")
    private String email;
    @NotBlank(message = "Password must be not null")
    private String password;
}
