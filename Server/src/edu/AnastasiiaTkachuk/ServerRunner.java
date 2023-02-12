package edu.AnastasiiaTkachuk;

public class ServerRunner {
    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer(7777, 100);
        chatServer.run();
    }
}
