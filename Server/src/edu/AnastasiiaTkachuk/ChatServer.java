package edu.AnastasiiaTkachuk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {

    private final ExecutorService pool;
    private final int port;
    private boolean stopped;

    public ChatServer(int port, int poolSize) {
        this.port = port;
        this.pool = Executors.newFixedThreadPool(poolSize);
    }

    public void run() throws IOException {
        System.out.println("SERVER STARTED");

        try (
                ServerSocket serverSocket = new ServerSocket(7777);
                Socket socket = serverSocket.accept();
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());

                //Scanner scanner = new Scanner(System.in)
        ) {

            System.out.printf("Connection accepted from %s:%s%n", socket.getInetAddress(), socket.getPort());

            String request = inputStream.readUTF();

            while (!request.contains("Stop server request")) {
                System.out.println("Client request: " + request);
//                String response = scanner.nextLine();
                outputStream.writeUTF("Message received at " + Timestamp.from(Instant.now()));
                request = inputStream.readUTF();
            }
            outputStream.writeUTF("Quit accepted at " + Timestamp.from(Instant.now()));
            request = inputStream.readUTF();
            while (!request.contains("accepted")) {

            }
        } catch (
                SocketException e) {
            if (e.getMessage().equals("Connection reset")) {
                System.out.println("Connection to client lost");
            }
        }
        System.out.println("SERVER STOPPED");

    }
}
