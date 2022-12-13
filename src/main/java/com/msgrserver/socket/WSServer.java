package com.msgrserver.socket;

import jakarta.websocket.DeploymentException;
import org.glassfish.tyrus.server.Server;

import java.util.Scanner;

public class WSServer {
    public static void main(String[] args) {
        Server server = new Server("localhost", 8086, "/", null, WSServerEndpoint.class);

        try {
            server.start();
            System.out.println("[SERVER]: Server is up and running.....");
            System.out.println("[SERVER]: Press 't' to terminate server.....");
            Scanner scanner = new Scanner(System.in);
            String inp = scanner.nextLine();
            scanner.close();
            if (inp.equalsIgnoreCase("t")) {
                System.out.println("[SERVER]: Server successfully terminated.....");
                server.stop();
            } else {
                System.out.println("[SERVER]: Invalid input!!!!!");
            }
        } catch (DeploymentException e) {
            e.printStackTrace();
        }
    }
}