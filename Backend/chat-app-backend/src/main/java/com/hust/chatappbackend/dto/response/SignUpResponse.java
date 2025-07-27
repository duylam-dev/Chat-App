package com.hust.chatappbackend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignUpResponse {
    private String email;
    private String fullName;
    private String profilePicture;
}
