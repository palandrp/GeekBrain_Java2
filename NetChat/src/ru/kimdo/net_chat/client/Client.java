package ru.kimdo.net_chat.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Pavel Petrikovskiy
 * @version 03.07.17
 */
class Client implements IConstantsClient {

    private BufferedReader reader;
    private Scanner scanner = new Scanner(System.in); // for keyboard input
    private String message;

    public static void main(String[] args) {
        new Client();
    }

    private Client() {
        Socket socket;
        PrintWriter writer;

        System.out.println(CONNECT_TO_SERVER);
        try {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer.println(getLoginAndPassword()); // send authentication data
            writer.flush();
            new Thread(new ServerListener()).start();
            do {
                message = scanner.nextLine();
                writer.println(message);
                writer.flush();
            } while (!message.equals(EXIT_COMMAND));
            socket.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        System.out.println(CONNECT_CLOSED);
    }

    /**
     * getLoginAndPassword: get login and password
     */
    String getLoginAndPassword() {
        System.out.print(LOGIN_PROMPT);
        String login = scanner.nextLine();
        System.out.print(PASSWD_PROMPT);
        return AUTH_SIGN + " " + login + " " + scanner.nextLine();
    }

    /**
     * ServerListener: get messages from Server
     */
    class ServerListener implements Runnable {
        @Override
        public void run() {
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.print(message.equals("\0")?
                            CLIENT_PROMPT : message + "\n");
                    if (message.equals(AUTH_FAIL))
                        System.exit(-1); // terminate client
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}
