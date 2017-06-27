package ru.kimdo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
        Thread t1 = new Thread(new MyLittleServer());
        t1.start();
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
    private Scanner in;
    private static int CLIENTS_COUNT = 0;
    private String name;

    ClientHandler(Socket s) {
        try {
            this.s = s;
            out = new PrintWriter(s.getOutputStream());
            in = new Scanner(s.getInputStream());
            CLIENTS_COUNT++;
            name = "Клиент #" + CLIENTS_COUNT;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void run() {
        while (true) {
            if(in.hasNext()) {
                String w = in.nextLine();
                System.out.println(name + ": " + w);
                out.println("echo: " + w);
                out.flush();
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
class MyLittleClient extends JFrame {
    private JTextField jtf;
    private JTextArea jta;
    private Socket sock;
    private Scanner in;
    private PrintWriter out;

    MyLittleClient() {
        final String SERVER_ADDR = "localhost";
        final int SERVER_PORT = 8189;

        try {
            sock = new Socket(SERVER_ADDR, SERVER_PORT);
            in = new Scanner(sock.getInputStream());
            out = new PrintWriter(sock.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        setBounds(600, 300, 500, 500);
        setTitle("Client");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        jta = new JTextArea();
        jta.setEditable(false);
        jta.setLineWrap(true);
        JScrollPane jsp = new JScrollPane(jta);
        add(jsp, BorderLayout.CENTER);
        JPanel bottomPanel = new JPanel(new BorderLayout());
        add(bottomPanel, BorderLayout.SOUTH);
        JButton jbSend = new JButton("SEND");
        bottomPanel.add(jbSend, BorderLayout.EAST);
        jtf = new JTextField();
        bottomPanel.add(jtf, BorderLayout.CENTER);
        jbSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!jtf.getText().trim().isEmpty()) {
                    sendMsg();
                    jtf.grabFocus();
                }
            }
        });
        jtf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMsg();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        if (in.hasNext()) {
                            String w = in.nextLine();
                            if (w.equalsIgnoreCase("end session")) break;
                            jta.append(w);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                try {
                    out.println("end");
                    out.flush();
                    sock.close();
                    out.close();
                    in.close();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        });
        setVisible(true);
    }
    private void sendMsg() {
        String a = jtf.getText();
        out.println(a);
        out.flush();
        jtf.setText("");
    }
}