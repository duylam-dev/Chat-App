package com.hust.chatappbackend.service;

import com.hust.chatappbackend.document.UserDocument;
import com.hust.chatappbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;


    public UserDocument findUserByEmail(String email) {
        return userRepository.findById(email).orElseThrow(() -> new UsernameNotFoundException("User not exists!"));
    }
    public boolean checkIfUserExists(String email) {
        return userRepository.existsById(email);
    }
    public void save(UserDocument userDocument) {
        userRepository.save(userDocument);
    }
}
