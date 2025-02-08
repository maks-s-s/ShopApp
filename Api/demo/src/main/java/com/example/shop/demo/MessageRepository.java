package com.example.shop.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m ORDER BY m.id ASC LIMIT 1")
    Message findFirstMessage();

    Message findById(long id);

    @Query("SELECT m FROM Message m ORDER BY m.id ASC")
    List<Message> findAllOrderedById();
}
