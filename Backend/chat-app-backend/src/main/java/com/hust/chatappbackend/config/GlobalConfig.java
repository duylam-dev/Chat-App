package com.hust.chatappbackend.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class GlobalConfig {
    @Value("${jwt.key}")
    private String jwtKey;
    @Value("${jwt.accessToken.duration}")
    private long accessTokenDuration;
    @Value("${jwt.refreshToken.duration}")
    private long refreshTokenDuration;
}
