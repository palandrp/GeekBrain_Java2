package ru.kimdo.net_chat.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;

/**
 * @author Pavel Petrikovskiy
 * @version 03.07.17
 */

public class GUIClient extends JFrame implements ActionListener {

    private final String LOGIN_PROMPT = "Login: ";
    private final String PASSWD_PROMPT = "Passwd: ";
    private final String AUTH_SIGN = "auth";
    private final String AUTH_FAIL = "Authentication failure.";
    private final String EXIT_COMMAND = "exit";
    private final String TITLE_OF_PROGRAM = "Network chat";
    private final int START_LOCATION = 350;
    private final int WINDOW_WIDTH = 350;
    private final int WINDOW_HEIGHT = 450;
    private final String TITLE_BTN_ENTER = "Enter";

    private String SERVER_ADDR = "localhost";
    private int SERVER_PORT = 2048;

    private JTextArea dialogue;
    private JTextField command;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public static void main(String[] args) {
        new  GUIClient();
    }

    private GUIClient() {
        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, WINDOW_WIDTH, WINDOW_HEIGHT);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                try {
                    writer.println(EXIT_COMMAND);
                    writer.flush();
                    socket.close();
                } catch (Exception ex) {}
            }
        });
        dialogue = new JTextArea();
        dialogue.setLineWrap(true);
        dialogue.setEditable(false);
        JScrollPane scrollBar = new JScrollPane(dialogue);
        JPanel bp = new JPanel();
        bp.setLayout(new BoxLayout(bp, BoxLayout.X_AXIS));
        command = new JTextField();
        command.addActionListener(this);
        JButton enter = new JButton(TITLE_BTN_ENTER);
        enter.addActionListener(this);
        bp.add(command);
        bp.add(enter);
        add(BorderLayout.CENTER, scrollBar);
        add(BorderLayout.SOUTH, bp);
        Connect();
        LoginOn formLogin = new LoginOn();
        if (formLogin.getStatus())
            setVisible(true);
        else
            System.exit(-1);
        formLogin.dispose();
    }

    private void Connect() {
        try {
            socket = new Socket(SERVER_ADDR, SERVER_PORT);
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            new Thread(new ServerListener()).start();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    class ServerListener implements Runnable {
        String message;
        @Override
        public void run() {
            try {
                while ((message = reader.readLine()) != null) {
                    if (!message.equals("\0"))
                        dialogue.append(message + "\n");
                    if (message.equals(AUTH_FAIL))
                        System.exit(-1);
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (command.getText().trim().length() > 0) {
            writer.println(command.getText());
            writer.flush();
            command.setText("");
        }
        command.requestFocusInWindow();
    }

    public final class LoginOn extends JFrame implements ActionListener {
        private final int WINDOW_HEIGHT = 98;

        private boolean status;
        private JTextField fLogin;
        private JTextField fPass;
        String login;
        String pass;
        String message;

        LoginOn() {
            setTitle(TITLE_OF_PROGRAM);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setBounds(START_LOCATION, START_LOCATION, WINDOW_WIDTH, WINDOW_HEIGHT);
            setResizable(false);
            JPanel bp_login = new JPanel();
            JPanel bp_pass = new JPanel();
            JPanel bp_btn = new JPanel();
            bp_login.setLayout(new BoxLayout(bp_login, BoxLayout.X_AXIS));
            bp_pass.setLayout(new BoxLayout(bp_pass, BoxLayout.X_AXIS));
            bp_btn.setLayout(new BoxLayout(bp_btn, BoxLayout.X_AXIS));
            fLogin = new JTextField(LOGIN_PROMPT);
            fPass = new JTextField(PASSWD_PROMPT);
            JButton enter = new JButton(TITLE_BTN_ENTER);
            fLogin.addActionListener(this);
            fPass.addActionListener(this);
            enter.addActionListener(this);
            bp_login.add(fLogin);
            bp_pass.add(fPass);
            bp_btn.add(enter);
            add(BorderLayout.NORTH, bp_login);
            add(BorderLayout.CENTER, bp_pass);
            add(BorderLayout.SOUTH, bp_btn);
            setVisible(true);
            try {
                if ((message = reader.readLine()).equals(AUTH_FAIL))
                    status = false;
                else
                    status = true;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NullPointerException e2) {
                try {
                    socket.close();
                    System.out.println(AUTH_FAIL);
                    System.exit(-1);
                }
                catch (IOException ex) { ex.printStackTrace(); }
            }
        }

        private String getLoginAndPassword() {
            login = fLogin.getText();
            pass = fPass.getText();
            return AUTH_SIGN + " " + login + " " + pass;
        }

        @Override
        public void actionPerformed(ActionEvent event) {
            writer.println(getLoginAndPassword());
            writer.flush();
        }

        boolean getStatus() {
            return status;
        }
    }
}