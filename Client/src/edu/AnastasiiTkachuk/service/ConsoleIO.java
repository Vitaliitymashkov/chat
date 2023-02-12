package edu.AnastasiiTkachuk.service;

import java.io.*;
import java.util.Scanner;

class ConsoleIO implements Runnable {
    String input = "";
    boolean startRead;
    public ConsoleIO() {
        new Thread(this).start();
    }

    public void run() {
        startRead = false;
        int j,avail;
        byte buf[] = new byte[1024];
        while (true) {
            if (startRead) {
                try {
                    avail = System.in.read(buf,0,buf.length);

                    // loop to avail-1 to avoid newline
                    for (j = 0;j < avail-1;j++) {
                        input+=(char)buf[j];
                    }
                    avail = 0;
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                startRead = false;
            }
            try {
                Thread.sleep(100);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void read() {
        System.out.print(""); // absolutely necessary, for some reason
        if (!startRead) {
            startRead = true;
        }
    }
    public String getData() {
        return new String(input);
    }

    public boolean hasData() {
        return (input.length() > 0);
    }
    public void reset() {
        startRead = false;
        input = "";
    }
}
