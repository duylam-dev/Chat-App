package com.hust.chatappbackend.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.Instant;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateProfileResponse {
    private String uuid;
    private String fileName;
    private Instant uploadedAt;
}
