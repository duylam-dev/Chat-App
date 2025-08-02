package com.hust.chatappbackend.controller;

import com.hust.chatappbackend.dto.ApiResponseCustom;
import com.hust.chatappbackend.dto.request.GetAccessTokenRequest;
import com.hust.chatappbackend.dto.request.LoginRequest;
import com.hust.chatappbackend.dto.request.SignUpRequest;
import com.hust.chatappbackend.dto.response.GetAccessTokenResponse;
import com.hust.chatappbackend.dto.response.LoginResponse;
import com.hust.chatappbackend.dto.response.SignUpResponse;
import com.hust.chatappbackend.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponseCustom<SignUpResponse>> login(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(ApiResponseCustom.<SignUpResponse>builder()
                        .code(HttpStatus.CREATED.value())
                        .message("Successfully created")
                        .data(authService.signup(signUpRequest))
                        .build()
                );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseCustom<LoginResponse>> login(@Valid @RequestBody LoginRequest loginRequest) {
        var res = authService.login(loginRequest);
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, res.getSecond().toString())
                .body(ApiResponseCustom.<LoginResponse>builder()
                        .code(HttpStatus.OK.value())
                        .message("Login successful")
                        .data(res.getFirst())
                        .build()
                );
    }


    @PostMapping("/logout")
    public ResponseEntity<ApiResponseCustom<Void>> logout() {
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, authService.logout().toString())
                .body(ApiResponseCustom.<Void>builder()
                        .code(HttpStatus.OK.value())
                        .message("Logout successful!")
                        .build()
                );
    }

    @PostMapping("/get-access-token")
    public ResponseEntity<ApiResponseCustom<GetAccessTokenResponse>> getAccessToken(@Valid @RequestBody GetAccessTokenRequest getAccessTokenRequest) {
        return ResponseEntity.ok(ApiResponseCustom.<GetAccessTokenResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Get new access successful!")
                .data(authService.getAccessToken(getAccessTokenRequest))
                .build()
        );
    }
}