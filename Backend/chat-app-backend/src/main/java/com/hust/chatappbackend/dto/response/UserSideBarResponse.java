package com.hust.chatappbackend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserSideBarResponse {
    private String email;
    private String fullName;
    private String profilePicture;
}
