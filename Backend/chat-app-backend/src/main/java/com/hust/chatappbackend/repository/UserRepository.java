package com.hust.chatappbackend.repository;

import com.hust.chatappbackend.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<UserDocument, String> {
    List<UserDocument> findUserByEmailNot(String email);
}
