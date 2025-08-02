package com.hust.chatappbackend.dto.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SendMessageRequest {
    private String text;
    private MultipartFile image;
}
