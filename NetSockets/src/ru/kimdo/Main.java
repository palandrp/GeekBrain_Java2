package ru.kimdo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author Pavel Petrikovskiy
 * @version 27.06.17.
 *
 * Написать консольный вариант клиент\серверного приложения, в котором пользователь
 * может писать сообщения, как на клиентской стороне, так и на серверной. Т.е. если
 * на клиентской стороне написать "Привет", нажать Enter, то сообщение должно
 * передаться на сервер и там отпечататься в консоли. Если сделать то же самое на
 * серверной стороне, сообщение, соответственно, передаётся клиенту и печатается у
 * него в консоли. Есть одна особенность, которую нужно учитывать: клиент или сервер
 * может написать несколько сообщений подряд, такую ситуацию необходимо корректно
 * обработать.
 */

public class Main {

    public static void main(String[] args) {
//        new MyLittleServer().run();
        new MyLittleClient();
    }
}
class MyLittleServer implements Runnable {
    public void run(){
        ServerSocket server = null;
        Socket s = null;
        Thread client = null;

        try {
            server = new ServerSocket(8189);
            System.out.println("Сервер запущен. Ожидание клиентов...");
                s = server.accept();
                System.out.println("Клиент подключился");
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
                System.out.println("Server closed");
                s.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
class ClientHandler implements Runnable {
    private Socket s;
    private PrintWriter out;
    private Scanner in, s_input;
    private static int CLIENTS_COUNT = 0;
    private String name;

    ClientHandler(Socket s) {
        s_input = new Scanner(System.in);

        try {
            this.s = s;
            out = new PrintWriter(s.getOutputStream());
            in = new Scanner(s.getInputStream());
            CLIENTS_COUNT++;
            name = "Клиент #" + CLIENTS_COUNT;
            System.out.println("Клиент хендлер \"" + name + "\" готов к приёму");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        while (true) {
            if(in.hasNext()) {
                String w = in.nextLine();
                System.out.println(name + ": " + w);
                if(w.equalsIgnoreCase("END"))
                    break;
            }
            out.println("Server: " + s_input.nextLine());
            out.flush();
        }
        try {
            System.out.println("Клиент отключился");
            s.close();
            s_input.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
class MyLittleClient {
    private Socket sock;
    private Scanner in, c_input;
    private PrintWriter out;

    MyLittleClient() {
        final String SERVER_ADDR = "localhost";
        final int SERVER_PORT = 8189;
        c_input = new Scanner(System.in);

        try {
            sock = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new Scanner(sock.getInputStream());
            out = new PrintWriter(sock.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            while (true) {
                if (in.hasNext()) {
                    String w = in.nextLine();
                    System.out.println(w);
                    if (w.equalsIgnoreCase("end session")) break;
                }
                out.println("Client: " + c_input.nextLine());
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            out.println("end");
            out.flush();
            sock.close();
            out.close();
            in.close();
            c_input.close();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
    private void sendMsg() {
        String a = c_input.nextLine();
        out.println("Client:" + a);
        out.flush();
    }
}