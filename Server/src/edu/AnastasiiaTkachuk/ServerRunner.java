package edu.AnastasiiaTkachuk;

import com.sun.net.httpserver.HttpServer;
import org.w3c.dom.ls.LSOutput;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Scanner;

public class ServerRunner {
    public static void main(String[] args) throws IOException {
        ChatServer chatServer = new ChatServer(7777, 100);
        chatServer.run();
    }
}
