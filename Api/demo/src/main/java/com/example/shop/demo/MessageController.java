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
        //get all messages
        List<Message> messageList = messageRepository.findAll();
        // get 50 last messages
        List<Message> messageListWith50messages = new ArrayList<>();
        if (messageList.size() > 51) {
            for (int i = 0; i < 50; i++) {
                messageListWith50messages.add(messageList.get(messageList.size() - 1 - (50 - i)));
            }
            return ResponseEntity.status(HttpStatus.OK).body(messageListWith50messages);
        }
        return ResponseEntity.status(HttpStatus.OK).body(messageList);
    }

    @PostMapping("/addNewMessage")
    public Message addNewMessage(@RequestBody Message message) {
        return messageService.addMessage(message);
    }
}
