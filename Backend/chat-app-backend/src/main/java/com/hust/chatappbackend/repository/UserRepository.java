package com.hust.chatappbackend.repository;

import com.hust.chatappbackend.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDocument, String> {
}
