package edu.AnastasiiTkachuk;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.Instant;
import java.util.Scanner;

public class ClientRunner {

    public static final String CLIENT_MESSAGE_FIELD_TEMPLATE = "You are [%s]. Enter your message (use 1 to change name or 8 to quit or 9 to stop server): ";

    public static void main(String[] args) throws IOException {
        System.out.print("Select your name: ");
        Scanner scanner1 = new Scanner(System.in);
        String userName = scanner1.nextLine();


            InetAddress inetAddress = Inet4Address.getByName("localhost");
        try (Socket socket = new Socket(inetAddress, 7777);
             DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
             DataInputStream inputStream = new DataInputStream(socket.getInputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.printf(CLIENT_MESSAGE_FIELD_TEMPLATE, userName);
            while (scanner.hasNextLine()){
                String request = scanner.nextLine();
                if (request.equals("1")) {
                    System.out.printf("You are [%s]. Select your name: ", userName);
                    userName = scanner1.nextLine();
                } else if (request.equals("8")) {
                    outputStream.writeUTF(Timestamp.from(Instant.now()) + " : " + userName + " : " + "Quitting...");
                    System.out.println("Quiting...");
                    System.exit(0);
                } else if (request.equals("9")) {
                    outputStream.writeUTF(Timestamp.from(Instant.now()) + " : " + userName + " : " + "Stop server request");
                    System.out.println("Quiting...");
                    scanner.hasNextLine();
                    while (!scanner.nextLine().contains("accepted")) {

                    }
                    if (scanner.nextLine().equalsIgnoreCase("Quit accepted at")) {
                        outputStream.writeUTF(Timestamp.from(Instant.now()) + " : " + userName + " : " + "accepted");
                        System.exit(0);
                    }
                }

                outputStream.writeUTF(Timestamp.from(Instant.now()) + " : " + userName + " : " + request);

                System.out.println("Response from server: " + inputStream.readUTF());

                System.out.printf(CLIENT_MESSAGE_FIELD_TEMPLATE, userName);
            }
        }
    }
}
