package com.hust.chatappbackend.service;

import com.hust.chatappbackend.config.GlobalConfig;
import com.hust.chatappbackend.document.UserDocument;
import com.hust.chatappbackend.dto.request.LoginRequest;
import com.hust.chatappbackend.dto.request.SignUpRequest;
import com.hust.chatappbackend.dto.response.LoginResponse;
import com.hust.chatappbackend.dto.response.SignUpResponse;
import com.hust.chatappbackend.exception.AppException;
import com.hust.chatappbackend.repository.UserRepository;
import com.hust.chatappbackend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpCookie;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {
    private final GlobalConfig globalConfig;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final SecurityUtil securityUtil;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public SignUpResponse signup(SignUpRequest signUpRequest) {
        if (userService.checkIfUserExists(signUpRequest.getEmail())) throw new AppException("User already exists");
        var newUser = UserDocument.builder()
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .fullName(signUpRequest.getFullName())
                .profilePicture(signUpRequest.getProfilePicture())
                .build();
        userService.save(newUser);
        return SignUpResponse.builder()
                .email(newUser.getEmail())
                .fullName(newUser.getFullName())
                .profilePicture(newUser.getProfilePicture())
                .build();
    }

    public Pair<LoginResponse, HttpCookie> login(LoginRequest loginRequest) {
        // Nạp input gồm username/password vào Security
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(), loginRequest.getPassword());
        // xác thực người dùng => cần viết hàm loadUserByUsername
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        log.info("Authentication success!");
        // save to spring security
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var accessToke = securityUtil.generateToken(authentication.getName(), false);
        var res = LoginResponse.builder()
                .accessToken(accessToke)
                .build();
        String refreshToken = securityUtil.generateToken(authentication.getName(), true);
        // save to database
        var userLogin = userService.findUserByEmail(authentication.getName());
        userLogin.setRefreshToken(refreshToken);
        userService.save(userLogin);

        // set cookie
        ResponseCookie cookie = ResponseCookie
                .from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(globalConfig.getRefreshTokenDuration())
                .build();

        return Pair.of(res, cookie);
    }
}
