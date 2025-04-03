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

    private Message lastPinnedMessage;

    @GetMapping("/getAllMessages")
    public ResponseEntity<List<Message>> getAllMessages() {
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

    @PutMapping("/setPinned")
    public void setPinned(long id) {
        Message message = messageRepository.findById(id);

        if (lastPinnedMessage == null) {
            List<Message> messageList = messageRepository.findAllOrderedById();
            for (Message m : messageList) {
                if (m.isEdited()) {
                    m.setEdited(false);
                    messageService.save(m);
                }
            }
        } else {
            lastPinnedMessage.setPinned(false);
            messageService.save(lastPinnedMessage);
        }
        
        message.setPinned(true);
        messageService.save(message);
        lastPinnedMessage = message;
    }

    @GetMapping("/isPinnedById")
    public ResponseEntity<Boolean> isPinnedById(long id) {
        Message message = messageRepository.findById(id);

        return ResponseEntity.status(HttpStatus.OK).body(message.isPinned());

    }

    @PutMapping("/setUnPinned")
    public void setUnPinned(long id) {
        Message message = messageRepository.findById(id);
        
        message.setPinned(false);
        messageService.save(message);
    }

    @GetMapping("/getMessageById")
    public ResponseEntity<Message> getMessageById(long id) {
        Message message = messageRepository.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(message);
    }
    
}
