package edu.AnastasiiTkachuk.service;

import java.io.DataOutput;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Scanner;

public class ConsoleReader implements Runnable{

    private final String CLIENT_MESSAGE_FIELD_TEMPLATE;
    private final Scanner scanner;
    private String userName;
    private Boolean stopped = false;
    private final DataOutput outputStream;

    public ConsoleReader(String client_message_field_template, Scanner scanner, String userName, DataOutput outputStream, Boolean stopped) {
        CLIENT_MESSAGE_FIELD_TEMPLATE = client_message_field_template;
        this.scanner = scanner;
        this.userName = userName;
        this.stopped = stopped;
        this.outputStream = outputStream;
    }

    @Override
    public void run() {

        while(!stopped) {
            while(scanner.hasNextLine()) {
                String request = scanner.nextLine();
                if (request.equals("1")) {
                    System.out.printf("Now you are [%s]. Select your new name: ", userName);
                    userName = scanner.nextLine();
                    System.out.printf(CLIENT_MESSAGE_FIELD_TEMPLATE);
                } else if (request.equals("8")) {
                    stopped = true;
                    try {
                        outputStream.writeUTF(Timestamp.from(Instant.now()) + " : " + userName + " : " + "Quitting...");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("Quiting...");
                    System.exit(0);
                } else {

                    String message = String.format("User [%s] says at [%s] : %s", userName, Timestamp.from(Instant.now()), request);
                    try {
                        outputStream.writeUTF(message);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    //System.out.printf("Sent to server: %s%n", message);
                }
            }
        }
    }

    public void control(Boolean stopped) {
        this.stopped = stopped;
    }
}
