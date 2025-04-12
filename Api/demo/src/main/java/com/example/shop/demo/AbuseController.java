package com.example.shop.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/abuse")
public class AbuseController {

    @Autowired
    private AbuseService abuseService;
    @Autowired
    private AbusesRepository abusesRepository;
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/addNewAbuse")
    public ResponseEntity<?> addNewAbuse(@RequestParam String email, @RequestParam Long messageId, @RequestParam String time, @RequestParam String reason, @RequestParam String description) {
        User sender = userRepository.findByEmail(email);
        if (sender == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
        }

        Message message = messageRepository.findById(messageId).orElse(null);
        if (message == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Message not found");
        }

        Abuse abuse = new Abuse();
        abuse.setSender(sender);
        abuse.setMessage(message);
        abuse.setTime(time);
        abuse.setReason(reason);
        abuse.setDescription(description);
        abusesRepository.save(abuse);

        return ResponseEntity.status(HttpStatus.CREATED).body("Abuse reported successfully");
    }

}
