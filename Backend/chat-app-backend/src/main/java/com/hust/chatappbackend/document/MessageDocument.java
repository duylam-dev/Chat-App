package com.hust.chatappbackend.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("message")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class MessageDocument {
    @DBRef
    private UserDocument sender;
    @DBRef
    private UserDocument receiver;
    private String text;
    private String imageUrl;
    @CreatedDate
    private Date date;

}
