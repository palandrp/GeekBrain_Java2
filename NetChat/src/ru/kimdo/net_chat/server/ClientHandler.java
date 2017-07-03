package ru.kimdo.net_chat.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Pavel Petrikovskiy
 * @version 03.07.17
 */
class ClientHandler implements Runnable, IConstantsServer {
    private Socket s;
    private PrintWriter out;
    private BufferedReader in;
    private Scanner s_input;
    private static int CLIENTS_COUNT = 0;
    private String name;

    ClientHandler(Socket s) {
        s_input = new Scanner(System.in);

        try {
            this.s = s;
            out = new PrintWriter(s.getOutputStream());
            in = new BufferedReader(
                    new InputStreamReader(s.getInputStream()));
            CLIENTS_COUNT++;
            name = "Клиент #" + CLIENTS_COUNT;
            System.out.println("Клиент хендлер \"" + name + "\" готов к приёму");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        Thread output = new Thread(new Runnable() {
            @Override
            public void run() {
                String message = "";
                try {
                    while (true) {
                        message = name + ": " + in.readLine();
                        System.out.println(message);
                        if (message.equalsIgnoreCase(EXIT_COMMAND)) {
                            out.println(SERVER_STOP);
                            out.flush();
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }); output.start();
        while (output.isAlive()) {
            out.println("Server: " + s_input.nextLine());
            out.flush();
        }
        try {
            System.out.println(CLIENT_DISCONNECTED);
            s.close();
            s_input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}