package com.example.shop.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AbusesRepository extends JpaRepository<Abuse, Long> {
    @Query("SELECT a FROM Abuse a WHERE a.messageId = :messageId")
    List<Abuse> findByMessageId(Long messageId);
}
