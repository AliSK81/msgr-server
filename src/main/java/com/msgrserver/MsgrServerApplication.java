package com.msgrserver;

import com.msgrserver.socket.WSServerEndpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MsgrServerApplication {

    public static void main(String[] args) {

        SpringApplication.run(MsgrServerApplication.class);
    }
}