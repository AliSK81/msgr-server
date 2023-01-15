package com.msgrserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;

@EnableWebSocket
@SpringBootApplication
public class MsgrServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(MsgrServerApplication.class);
    }
}