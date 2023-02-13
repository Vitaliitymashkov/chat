package edu.AnastasiiTkachuk.service;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Client {

    public static final int PORT = 7777;
    private final ExecutorService pool = Executors.newFixedThreadPool(2);
    private Boolean stopped = false;
    public final String CLIENT_MESSAGE_FIELD_TEMPLATE =
//            "You are [%s]. Enter your message (use 1 to change name or 8 to quit or 9 to stop server): ";
            "Enter your message (use 1 to change name or 8 to quit): ";

    public /*synchronized*/ void go() throws IOException {
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

            Thread io = new Thread(new ConsoleReader(CLIENT_MESSAGE_FIELD_TEMPLATE, new Scanner(System.in), userName, outputStream, stopped));
            Thread nio = new Thread(new NetworkReader(CLIENT_MESSAGE_FIELD_TEMPLATE, userName, inputStream));
            pool.submit(io);
            pool.submit(nio);

//            io.start();
//            nio.start();

            while (!stopped) {
//                io.control(stopped);
//                nio.control(stopped);


                    //System.out.println("Finish your input!");
                    //System.out.println("Waiting for network.");
                    Thread.sleep(2000);


            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
