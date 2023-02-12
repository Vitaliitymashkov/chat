package edu.AnastasiiTkachuk.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Client {

    public static final int PORT = 7777;
    private final ExecutorService pool = Executors.newFixedThreadPool(2);
    private boolean stopped = false;
    public final String CLIENT_MESSAGE_FIELD_TEMPLATE =
//            "You are [%s]. Enter your message (use 1 to change name or 8 to quit or 9 to stop server): ";
            "You are [%s]. Enter your message (use 1 to change name or 8 to quit): ";

    public synchronized void go() throws IOException {
        InetAddress inetAddress = Inet4Address.getByName("localhost");
        System.out.printf("Connecting to %s:%s%n", inetAddress, PORT);
        System.out.print("Select your name: ");
        Scanner scanner1 = new Scanner(System.in);
        String userName = scanner1.nextLine();

        try (
                Socket socket = new Socket(inetAddress, PORT);
                DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        ) {

            System.out.printf(CLIENT_MESSAGE_FIELD_TEMPLATE, userName);

            ConsoleIO io = new ConsoleIO();
            NetworkStreamIO nio = new NetworkStreamIO(inputStream);
            pool.submit(io);
            pool.submit(nio);

            while (!stopped) {
                io.read();
                nio.read();

                while (!io.hasData() && !nio.hasData()) {
                    System.out.println("Finish your input!");
                    System.out.println("Waiting for network.");
                    Thread.sleep(2000);
                }

                if (io.hasData()) {
                    String request = io.getData();
                    if (request.equals("1")) {
                        System.out.printf("You are [%s]. Select your name: ", userName);
                        userName = scanner1.nextLine();
                    } else if (request.equals("8")) {
                        stopped = true;
                        outputStream.writeUTF(Timestamp.from(Instant.now()) + " : " + userName + " : " + "Quitting...");
                        System.out.println("Quiting...");
                    }

                    String message = String.format("[%s] : Name [%s] : Text [%s].",Timestamp.from(Instant.now()), userName, request);
                    outputStream.writeUTF(message);
                    System.out.printf("Sent to server: %s%n", message);

                    //DISABLED
                    //System.out.println("Response from server: " + inputStream.readUTF());

                    System.out.printf(CLIENT_MESSAGE_FIELD_TEMPLATE, userName);
                }

                if (nio.hasData()) {
                    String dataNio = nio.getData();
                    System.out.printf("%nResponse from server: %s%n", dataNio);
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
