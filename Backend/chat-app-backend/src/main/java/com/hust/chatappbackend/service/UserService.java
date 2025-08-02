package com.hust.chatappbackend.service;

import com.hust.chatappbackend.document.UserDocument;
import com.hust.chatappbackend.dto.response.UpdateProfileResponse;
import com.hust.chatappbackend.dto.response.UserSideBarResponse;
import com.hust.chatappbackend.exception.AppException;
import com.hust.chatappbackend.repository.UserRepository;
import com.hust.chatappbackend.util.MinioUtil;
import com.hust.chatappbackend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SecurityUtil securityUtil;
    private final MinioUtil minioUtil;

    public UserDocument findUserByEmail(String email) {
        return userRepository.findById(email).orElseThrow(() -> new UsernameNotFoundException("User not exists!"));
    }

    public boolean checkIfUserExists(String email) {
        return userRepository.existsById(email);
    }

    public void save(UserDocument userDocument) {
        userRepository.save(userDocument);
    }

    public UpdateProfileResponse updateProfile(MultipartFile file) {
        String email = securityUtil.getCurrentUserLogin().orElseThrow(() -> new AppException("Access token invalid!"));
        var userLogin = findUserByEmail(email);
        var path = minioUtil.save(file, UUID.randomUUID());
        userLogin.setProfilePicture(path);
        save(userLogin);

        return UpdateProfileResponse.builder()
                .uuid(path)
                .fileName(file.getOriginalFilename())
                .uploadedAt(Instant.now())
                .build();
    }


    public InputStream getProfilePicture(UUID uuid) {
        return minioUtil.getInputStream(uuid, 0, -1);
    }


    public List<UserSideBarResponse> getUserSideBar() {
        String email = securityUtil.getCurrentUserLogin().orElseThrow(() -> new AppException("Access token invalid!"));
        return userRepository.findUserByEmailNot(email).stream().map(i -> UserSideBarResponse.builder()
                .email(i.getEmail())
                .fullName(i.getFullName())
                .profilePicture(i.getProfilePicture())
                .build()).toList();
    }
}
