package com.hust.chatappbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GetAccessTokenRequest {
    @NotBlank(message = "Refresh token is required")
    private String refresh_token;
}
