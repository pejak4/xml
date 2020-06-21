package com.example.messageservice.repository;

import com.example.messageservice.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    List<Message> findAllBySenderId(Long id);
    List<Message> findAllByReceiverId(Long id);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "delete from message u where u.id=:id")
    void deleteMessage(@Param("id") Long id);

}
