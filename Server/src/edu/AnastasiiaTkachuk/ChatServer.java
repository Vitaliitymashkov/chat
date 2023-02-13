package edu.AnastasiiaTkachuk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {

    private final List<DataOutputStream> clientDataOutputStreams = new ArrayList<>();
    private final ExecutorService pool;
    private final int poolSize;
    private final int port;
    private boolean stopped;

    public ChatServer(int port, int poolSize) {
        this.port = port;
        this.poolSize = poolSize;
        this.pool = Executors.newFixedThreadPool(poolSize);
    }

    public void run() {

        try (ServerSocket server = new ServerSocket(port)) {
            System.out.printf("SERVER STARTED with %s max connections%n", poolSize);
            while (!stopped) {
                System.out.printf("Accepting connections on %s:%s%n", server.getInetAddress(), port);
                Socket socket = server.accept();
                System.out.printf("Connection accepted from %s:%s%n", socket.getInetAddress(), socket.getPort());
                pool.submit(() -> processSocket(socket));
            }
            System.out.println("SERVER STOPPED");
        } catch (IOException e) {
            System.out.println("SERVER FAILED WITH EXCEPTION " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void processSocket(Socket socket) {
        try (socket;
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        ) {
            clientDataOutputStreams.add(outputStream);

            String request = inputStream.readUTF();

            while (!request.contains("Stop server request")) {
                System.out.println("Client's request: " + request);

                //DISABLED
//                System.out.println("Sending acknowledgement to the client");
//                outputStream.writeUTF("Message received at " + Timestamp.from(Instant.now()));

                //NOTE send this message to other pools (to display)
                List<Task> taskList = new ArrayList<>();
                taskList.add(new Task(request, clientDataOutputStreams));
                try {
                    pool.invokeAll(taskList);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

                request = inputStream.readUTF();
            }
        } catch (SocketException e) {
            if (e.getMessage().equals("Connection reset")) {
                System.out.printf("Connection to client %s:%s lost%n", socket.getInetAddress(), socket.getPort());
                pool.shutdown();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
}
