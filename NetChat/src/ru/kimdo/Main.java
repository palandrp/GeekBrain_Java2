package ru.kimdo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.sql.*;

/**
 * @author Pavel Petrikovskiy
 * @version 17.06.17
 */

public class Main extends JFrame implements ActionListener {
    private JTextArea dialogue;
    private JTextField message;
    private PrintWriter pw;

    public static void main(String[] args) {
        new Main();
    }
    private Main() {
        final String TITLE_OF_PROGRAM = "Network chat";
        final int START_LOCATION = 200;
        final int WINDOW_WIDTH = 350;
        final int WINDOW_HEIGHT = 450;

        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, WINDOW_WIDTH, WINDOW_HEIGHT);
        addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {
                pw.close();
            }
            @Override
            public void windowClosed(WindowEvent e) {}
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        dialogue = new JTextArea();
        dialogue.setEditable(true);
        JScrollPane scrollBar = new JScrollPane(dialogue);
        JPanel bp = new JPanel();
        bp.setLayout(new BoxLayout(bp, BoxLayout.X_AXIS));
        message = new JTextField();
        message.addActionListener(this);
        JButton enter = new JButton("Enter");
        enter.addActionListener(this);
        bp.add(message);
        bp.add(enter);
        add(BorderLayout.CENTER, scrollBar);
        add(BorderLayout.SOUTH, bp);
        setVisible(true);
        try {
            pw = new PrintWriter(new FileWriter("log.txt",true));
        } catch (IOException e) {
            dialogue.append("SYSTEM: Не удалось создать/открыть лог-файл!");
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (message.getText().trim().length() > 0) {
            dialogue.append(message.getText() + "\n");
            dialogue.append("Тестовый запуск!\n");
            pw.printf("%s\n",message.getText());
            pw.print("Тестовый запуск!\n");
            pw.flush();
        }
        message.setText("");
        message.requestFocusInWindow();
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
                        if (message.equalsIgnoreCase(name + ": END")) {
                            out.println("Server: end session");
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
    private BufferedReader in;
    private PrintWriter out;

    MyLittleClient() {
        final String SERVER_ADDR = "localhost";
        final int SERVER_PORT = 8189;
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
                        if (message.equalsIgnoreCase("Server: end session"))
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
}
class SQLite {

    static final String DRIVER_NAME = "org.sqlite.JDBC";
    static Connection connect = null;
    static String nameDB = "Forfan.db";
    static String tableDB = "COMPANY";

    public static void main(String[] args) {
        openDB(nameDB);
        createTable(tableDB);
        insertRecords(tableDB);
        selectRecords(tableDB);

        updateRecord(tableDB);
        selectRecords(tableDB);

        deleteRecord(tableDB);
        selectRecords(tableDB);
        try {
            connect.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    static void openDB(String nameDB) {
        try {
            Class.forName(DRIVER_NAME);
            connect = DriverManager.getConnection("jdbc:sqlite:" + nameDB);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Opening database " + nameDB + " successfully");
    }

    static void createTable(String table) {
        try {
            Statement stmt = connect.createStatement();
            String sql = "CREATE TABLE " + table +
                    "(ID INT PRIMARY KEY NOT NULL," +
                    " NAME   TEXT    NOT NULL," +
                    " AGE    INT NOT NULL," +
                    " ADDRESS   CHAR(50)," +
                    " SALARY    REAL)";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Create table in database " + nameDB + " successfully");
    }

    static void insertRecords(String table) {
        try {
            Statement stmt = connect.createStatement();
            String sql = "INSERT INTO " + table +
                    " (ID,NAME,AGE,ADDRESS,SALARY) " +
                    "VALUES (1, 'Paul', 32, 'California', 20000.00);";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO " + table +
                    " (ID,NAME,AGE,ADDRESS,SALARY) " +
                    "VALUES (2, 'Allen', 25, 'Texas', 15000.00);";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO " + table +
                    " (ID,NAME,AGE,ADDRESS,SALARY) " +
                    "VALUES (3, 'Teddy', 23, 'Norway', 20000.00);";
            stmt.executeUpdate(sql);

            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
        System.out.println("Records in database " + nameDB + " added successfully");
    }

    static void selectRecords(String table) {
        try {
            Statement stmt = connect.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * FROM " + table + ";" );
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String address = rs.getString("address");
                float salary = rs.getFloat("salary");
                System.out.println("ID = " + id );
                System.out.println("NAME = " + name);
                System.out.println("AGE = " + age);
                System.out.println("ADDRESS = " + address);
                System.out.println("SALARY = " + salary);
                System.out.println();
            }
            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    static void updateRecord(String table) {
        try {
            Statement stmt = connect.createStatement();
            String sql = "UPDATE " + table + " set SALARY = 35000.00 where ID=1;";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    static void deleteRecord(String table) {
        try {
            Statement stmt = connect.createStatement();
            String sql = "DELETE from " + table + " where ID=2;";
            stmt.executeUpdate(sql);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}