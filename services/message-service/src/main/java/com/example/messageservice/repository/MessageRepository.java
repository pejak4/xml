package com.example.messageservice.repository;

import com.example.messageservice.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message,Long> {

    Message findOneBySenderId(Long id);
    Message findOneByReceiverId(Long id);


}
