package com.hust.chatappbackend.service;

import com.hust.chatappbackend.document.MessageDocument;
import com.hust.chatappbackend.dto.request.SendMessageRequest;
import com.hust.chatappbackend.exception.AppException;
import com.hust.chatappbackend.repository.MessageRepository;
import com.hust.chatappbackend.util.MinioUtil;
import com.hust.chatappbackend.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final SecurityUtil securityUtil;
    private final UserService userService;
    private final MinioUtil minioUtil;

    public List<MessageDocument> getMessages(String emailReceiver) {
        String emailLogin = securityUtil.getCurrentUserLogin().orElseThrow(() -> new AppException("Access token invalid!"));
        var userLogin = userService.findUserByEmail(emailLogin);
        var userReceiver = userService.findUserByEmail(emailReceiver);

        return messageRepository.findMessageDocumentBySenderOrReceiver(userLogin, userReceiver, userReceiver, userLogin);
    }

    public MessageDocument sendMessage(String emailReceiver, SendMessageRequest sendMessageRequest) {
        String emailLogin = securityUtil.getCurrentUserLogin().orElseThrow(() -> new AppException("Access token invalid!"));
        var userLogin = userService.findUserByEmail(emailLogin);
        var userReceiver = userService.findUserByEmail(emailReceiver);
        String imageUrl = "";
        if(sendMessageRequest.getImage() != null){
            //upload
            imageUrl = minioUtil.save("file", sendMessageRequest.getImage(), UUID.randomUUID());
        }
        return MessageDocument.builder()
                .sender(userLogin)
                .receiver(userReceiver)
                .text(sendMessageRequest.getText())
                .imageUrl(imageUrl)
                .build();
    }
}
