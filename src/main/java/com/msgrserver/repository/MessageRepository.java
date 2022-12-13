package com.msgrserver.repository;

import com.msgrserver.model.entity.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {

}