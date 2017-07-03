package ru.kimdo.net_chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author Pavel Petrikovskiy
 * @version 03.07.17
 */
class Server implements Runnable, IConstantsServer {

    public static void main(String[] args) {
        new Server().run();
    }
    public void run(){
        ServerSocket server = null;
        Socket s = null;
        Thread client = null;

        try {
            server = new ServerSocket(SERVER_PORT);
            System.out.println(SERVER_START);
            s = server.accept();
            System.out.println(CLIENT_JOINED);
            client = new Thread(new ClientHandler(s));
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                client.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                server.close();
                System.out.println(SERVER_STOP);
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
