package com.example.shop.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping("/getAllMessages")
    public ResponseEntity<List<Message>> getPasswordByEmail() {
        List<Message> messageList = messageRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(messageList);
    }

    @PostMapping("/addNewMessage")
    public Message addNewMessage(@RequestBody Message message) {
        return messageService.addMessage(message);
    }
    @DeleteMapping("/clearChat")
    public void clearChat() {
        messageRepository.deleteAll();
    }
}
