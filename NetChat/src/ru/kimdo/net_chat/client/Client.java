package ru.kimdo.net_chat.client;

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
class Client implements IConstantsClient {
    private Socket sock;
    private BufferedReader in;
    private PrintWriter out;

    Client() {
        Scanner c_input = new Scanner(System.in);

        try {
            sock = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new BufferedReader(
                    new InputStreamReader(sock.getInputStream()));
            out = new PrintWriter(sock.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread output = new Thread(new Runnable() {
            @Override
            public void run() {
                String message = "";
                try {
                    while (true) {
                        message = in.readLine();
                        System.out.println(message);
                        if (message.equalsIgnoreCase(EXIT_COMMAND))
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }); output.start();
        try {
            while (output.isAlive()) {
                out.println(c_input.nextLine());
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            out.println(EXIT_COMMAND);
            out.flush();
            sock.close();
            out.close();
            in.close();
            c_input.close();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}
