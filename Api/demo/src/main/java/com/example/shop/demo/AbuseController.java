package com.example.shop.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    @Autowired
    private MessageService messageService;

    @PostMapping("/addNewAbuse")
    public ResponseEntity<?> addNewAbuse(@RequestBody Abuse abuse) {
        abusesRepository.save(abuse);
        return ResponseEntity.status(HttpStatus.CREATED).body("Abuse reported successfully");
    }

    @GetMapping("/getAllAbuses")
    public ResponseEntity<List<Abuse>> getAllMessages(long id) {
        List<Abuse> abuseList = abuseService.getAbusesByMessageId(id);
        return ResponseEntity.status(HttpStatus.OK).body(abuseList);
    }

    @DeleteMapping("/clearAbuses")
    public void clearAbuses() {abusesRepository.deleteAll();}

    @DeleteMapping("/deleteAbuse")
    public void deleteAbuse(long id) {
        Optional<Abuse> abuse = abusesRepository.findById(id);
        if (abuseService.getAbusesByMessageId(id).size() - 1 < 1) {
            if (abuse.isPresent()) {
                Message message = messageRepository.findById(abuse.get().getMessageId());
                message.setAbused(false);
                messageService.save(message);
            }
        }
        abusesRepository.deleteById(id);
    }
}
