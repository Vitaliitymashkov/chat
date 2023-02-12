package edu.AnastasiiTkachuk;

import edu.AnastasiiTkachuk.service.Client;

import java.io.IOException;

public class ClientRunner {
    public static void main(String[] args) throws IOException {
        new Client().go();
    }
}