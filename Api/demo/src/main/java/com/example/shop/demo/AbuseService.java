package com.example.shop.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbuseService {

    @Autowired
    private AbusesRepository abusesRepository;

    public Abuse addAbuse(Abuse a) {
        return abusesRepository.save(a);
    }


}
