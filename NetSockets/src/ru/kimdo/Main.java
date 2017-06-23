package ru.kimdo;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @author kimdo
 * @version 23.06.17.
 */
public class Main {

    public static void main(String[] args) {
        new MyLittleServer().go();
    }
}
class MyLittleServer {
    private ServerSocket server = null; // Создаём пустую ссылку на сервер
    private Socket s = null;            // Создаём пустую ссылку на сокет

    void go() {
        try {
            server = new ServerSocket(8189); // Запускаем сервер на прослушивание порта 8189
            System.out.println("Сервер запущен. Ожидание клиентов...");
            while (true) {
                s = server.accept(); // Как только клиент подключится, создаем сокет (соединение)
                System.out.println("Клиент подключился");
                new Thread(new ClientHandler(s)).start(); // В отдельном потоке запускаем обработчик этого клиента
            } // и переходим в режим ожидания следующего клиента (в начало цикла while)
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
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
    private Scanner in;
    private static int CLIENTS_COUNT = 0;
    private String name;

    ClientHandler(Socket s) {
        try {
            this.s = s; // при создании обработчика, даем ему ссылку на обрабатываемое соединение (сокет) 
            out = new PrintWriter(s.getOutputStream()); // PrintWriter служит для отсылки сообщений клиенту 
            in = new Scanner(s.getInputStream()); // Scanner предназначен для чтения сообщений от клиента 
            CLIENTS_COUNT++;                      // Подсчитываем количество клиентов 
            name = "Клиент #" + CLIENTS_COUNT;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() { // метод обмена сообщениями 
        while (true) { // запускаем бесконечный цикл
            if(in.hasNext()) { // если от клиента пришло сообщение
                String w = in.nextLine(); // читаем его 
                System.out.println(name + ": " + w); // печатаем это сообщение в консоль
                out.println("echo: " + w); // и отсылаем обратно с добавлением фразы "echo: "
                out.flush(); // если клиент прислал "end", выходим из бесконечного цикла
                if(w.equalsIgnoreCase("END"))
                    break;
            }
        }
        try {
            System.out.println("Клиент отключился");
            s.close(); // закрываем сокет
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
