package com.hust.chatappbackend.repository;

import com.hust.chatappbackend.document.MessageDocument;
import com.hust.chatappbackend.document.UserDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MessageRepository extends MongoRepository<MessageDocument, String> {
    List<MessageDocument> findMessageDocumentBySenderOrReceiver(
            UserDocument sender1, UserDocument receiver1,
            UserDocument sender2, UserDocument receiver2
    );
}
