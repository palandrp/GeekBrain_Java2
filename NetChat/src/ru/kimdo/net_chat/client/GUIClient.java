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

public class GUIClient extends JFrame implements ActionListener, IConstantsClient {

    private JTextArea dialogue; // area for dialog
    private JTextField command; // field for entering commands

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    public static void main(String[] args) {
        new  GUIClient();
    }

    /**
     * Constructor:
     * Creating a window and all the necessary elements on it
     */
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
        // area for dialog
        dialogue = new JTextArea();
        dialogue.setLineWrap(true);
        dialogue.setEditable(false);
        JScrollPane scrollBar = new JScrollPane(dialogue);
        // panel for connamd field and button
        JPanel bp = new JPanel();
        bp.setLayout(new BoxLayout(bp, BoxLayout.X_AXIS));
        command = new JTextField();
        command.addActionListener(this);
        JButton enter = new JButton(TITLE_BTN_ENTER);
        enter.addActionListener(this);
        // adding all elements to the window
        bp.add(command);
        bp.add(enter);
        add(BorderLayout.CENTER, scrollBar);
        add(BorderLayout.SOUTH, bp);
        setVisible(true);
        // connect to server
        Connect();
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

    /**
     * ServerListener: get messages from Server
     */
    class ServerListener implements Runnable {
        String message;
        @Override
        public void run() {
            try {
                while ((message = reader.readLine()) != null) {
                    if (!message.equals("\0"))
                        dialogue.append(message + "\n");
                    if (message.equals(AUTH_FAIL))
                        System.exit(-1); // terminate client
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    /**
     * Listener of events from menu, command field and enter button
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        if (command.getText().trim().length() > 0) {
            writer.println(command.getText());
            writer.flush();
            command.setText("");
        }
        command.requestFocusInWindow();
    }
}