package com.hust.chatappbackend.controller;

import com.hust.chatappbackend.document.MessageDocument;
import com.hust.chatappbackend.dto.ApiResponseCustom;
import com.hust.chatappbackend.dto.request.SendMessageRequest;
import com.hust.chatappbackend.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;


    @GetMapping("/{email}")
    public ResponseEntity<ApiResponseCustom<List<MessageDocument>>> getMessages(@PathVariable String email) {
        return ResponseEntity.ok(ApiResponseCustom.<List<MessageDocument>>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(messageService.getMessages(email))
                .build());
    }

    @PostMapping("/send/{email}")
    public ResponseEntity<ApiResponseCustom<MessageDocument>> sendMessage(@PathVariable String email,
                                                                          @ModelAttribute SendMessageRequest sendMessageRequest) {
        return ResponseEntity.ok(ApiResponseCustom.<MessageDocument>builder()
                .code(HttpStatus.OK.value())
                .message("success")
                .data(messageService.sendMessage(email, sendMessageRequest))
                .build());
    }
}
