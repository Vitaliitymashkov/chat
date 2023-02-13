package edu.AnastasiiTkachuk.service;

import java.io.DataInputStream;
import java.io.IOException;

public class NetworkReader implements Runnable {

    private final String CLIENT_MESSAGE_FIELD_TEMPLATE;
    private boolean stopped = false;
    private String userName;
    private DataInputStream inputStream;

    public NetworkReader(String client_message_field_template, String userName, DataInputStream inputStream) {
        CLIENT_MESSAGE_FIELD_TEMPLATE = client_message_field_template;
        this.userName = userName;
        this.inputStream = inputStream;
    }

    @Override
    public void run() {

        while (!stopped) {
            String dataNio;
            try {
                dataNio = inputStream.readUTF();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            System.out.printf("%nResponse from server: %s%n", dataNio);
            System.out.printf("%n%s%n", dataNio);
//            System.out.println();
            System.out.printf(CLIENT_MESSAGE_FIELD_TEMPLATE);
        }
    }

    public void control(Boolean stopped) {
        this.stopped = stopped;
    }

}
