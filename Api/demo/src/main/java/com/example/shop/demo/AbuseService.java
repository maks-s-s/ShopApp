package com.example.shop.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbuseService {

    @Autowired
    private AbusesRepository abusesRepository;

    public Abuse addAbuse(Abuse a) {
        return abusesRepository.save(a);
    }

    public List<Abuse> getAbusesByMessageId(Long messageId) {
        return abusesRepository.findByMessageId(messageId);
    }

}
