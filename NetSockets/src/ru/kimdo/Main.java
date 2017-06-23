package ru.kimdo;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author kimdo
 * @version 23.06.17.
 */
public class Main {

    public static void main(String[] args) {
        // write your code here
    }
}
class MyLiteServer {
    ServerSocket server = null; // Создаём пустую ссылку на сервер
    Socket s = null;            // Создаём пустую ссылку на сокет

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
    ClientHandler(Socket s) {
        
    }
    public void run(){

    }
}
