package com.hust.chatappbackend.util;

import com.hust.chatappbackend.config.GlobalConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityUtil {
    private final JwtEncoder jwtEncoder;
    private final JwtDecoder jwtDecoder;
    private final GlobalConfig globalConfig;
    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;


    public String generateToken(String email, boolean isRefresh) {
        Instant now = Instant.now();
        Instant validity = isRefresh ? now.plus(globalConfig.getRefreshTokenDuration(), ChronoUnit.SECONDS)
                : now.plus(globalConfig.getAccessTokenDuration(), ChronoUnit.SECONDS);
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validity)
                .subject(email)
                .build();
        JwsHeader jwsHeader = JwsHeader.with(JWT_ALGORITHM).build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).getTokenValue();
    }


}
