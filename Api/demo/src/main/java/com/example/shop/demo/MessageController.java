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
        List<Message> messageList = messageRepository.findAllOrderedById();
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

    @PutMapping("/deleteMessage")
    public void deleteMessage(long id, String ChangerEmail) {
        Message message = messageRepository.findById(id);
        message.setText("Massage was deleted...");
        message.setDeleted(true);
        message.setWasChanged(true);
        message.setChangerEmail(ChangerEmail);
        message.setTextColor("#DF2226");
        messageService.save(message);
    }

    @PutMapping("/editMesage")
    public void editMesage(long id, String ChangerEmail, String newText) {
        Message message = messageRepository.findById(id);
        message.setText(newText);
        message.setEdited(true);
        message.setWasChanged(true);
        message.setChangerEmail(ChangerEmail);
        messageService.save(message);
    }

    @GetMapping("/getIdForNextMessage")
    public ResponseEntity<Long> getIdForNextMessage() {
        List<Message> messageList = messageRepository.findAll();
        if (!messageList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.OK).body(messageList.get(messageList.size() - 1).getId());
        } else {
            return ResponseEntity.status(HttpStatus.OK).body((long)0);
        }
    }

    @PutMapping("/setUnChanged")
    public void setUnChanged(long id) {
        Message message = messageRepository.findById(id);
        message.setWasChanged(false);
        message.setChangerEmail("");
        messageService.save(message);
    }

    @PutMapping("/setChanged")
    public void setChanged(long id) {
        Message message = messageRepository.findById(id);
        message.setWasChanged(true);
        message.setChangerEmail(message.getSendersEmail());
        messageService.save(message);
    }


}
