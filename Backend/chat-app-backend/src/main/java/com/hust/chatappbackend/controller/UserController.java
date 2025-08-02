package com.hust.chatappbackend.controller;

import com.hust.chatappbackend.dto.ApiResponseCustom;
import com.hust.chatappbackend.dto.response.UpdateProfileResponse;
import com.hust.chatappbackend.dto.response.UserSideBarResponse;
import com.hust.chatappbackend.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/update-profile")
    public ResponseEntity<UpdateProfileResponse> updateProfile(
            @RequestParam(name = "file", required = false) MultipartFile file
    ) {
        return ResponseEntity.ok(userService.updateProfile(file));
    }

    @GetMapping("/avatar/{uuid}")
    public ResponseEntity<Void> getAvatar(@PathVariable UUID uuid, HttpServletResponse response) {
        try (InputStream is = userService.getProfilePicture(uuid)) {

            // Nếu bạn muốn trả về ảnh luôn thì có thể set MIME-type tạm là image/jpeg
            // Hoặc dùng application/octet-stream để trình duyệt tự xử lý
            response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
            response.setHeader("Content-Disposition", "inline; filename=\"" + uuid + "\"");

            // Cho phép gọi từ trình duyệt (nếu frontend khác domain)
            response.setHeader("Access-Control-Allow-Origin", "*");

            is.transferTo(response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            response.setStatus(404);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list-user-sidebar")
    public ResponseEntity<ApiResponseCustom<List<UserSideBarResponse>>> getUserSidebar() {
        return ResponseEntity.ok(ApiResponseCustom.<List<UserSideBarResponse>>builder()
                .code(HttpStatus.OK.value())
                .message("Success")
                .data(userService.getUserSideBar())
                .build());
    }
}
