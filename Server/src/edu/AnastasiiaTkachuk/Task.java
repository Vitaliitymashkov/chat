package edu.AnastasiiaTkachuk;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

class Task implements Callable<String> {
    private final String message;
    private final List<DataOutputStream> clientDataOutputStreams;

    public Task(String message, List<DataOutputStream> dataOutputStream) {
        this.message = message;
        this.clientDataOutputStreams = dataOutputStream;
    }

    @Override
    public String call(){
        System.out.printf("Display message from one client on another [%s]\n", this.message);
        //NOTE display message on clients

        clientDataOutputStreams.forEach(s -> {
            try {
                s.writeUTF(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return "";
    }
}
