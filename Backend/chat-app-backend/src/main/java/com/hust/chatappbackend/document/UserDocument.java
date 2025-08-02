package com.hust.chatappbackend.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document("user")
@Getter
@Setter
@AllArgsConstructor
@Builder
public class UserDocument {
    @Id
    private String email;
    private String fullName;
    private String password;
    private String profilePicture;
    private String refreshToken;
    @CreatedDate
    private Date createdAt;
    @LastModifiedDate
    private Date updatedAt;

}
