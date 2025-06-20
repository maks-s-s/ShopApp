package com.example.shop.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message addMessage(Message m) {
        return messageRepository.save(m);
    }

    public void deleteFirstMessage() {
        Message firstMessage = messageRepository.findFirstMessage();

        if (firstMessage != null) {
            messageRepository.delete(firstMessage);
        }
    }

    public Message findById (long id) {
        return messageRepository.findById(id);
    }

    public Message save(Message message) {
        return messageRepository.save(message);
    }
}
