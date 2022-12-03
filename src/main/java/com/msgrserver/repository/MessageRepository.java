package com.msgrserver.repository;

import com.msgrserver.model.entity.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Map;
import java.util.Set;

public interface MessageRepository extends JpaRepository<Message, Long> {

}