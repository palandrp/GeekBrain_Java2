package ru.kimdo.net_chat.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * @author Pavel Petrikovskiy
 * @version 03.07.17
 */

public class MakeWindow extends JFrame implements ActionListener, IConstantsClient {
    private JTextArea dialogue;
    private JTextField message;
    private PrintWriter logWriter;

    public static void main(String[] args) {
        MakeWindow window = new MakeWindow();
        new Client(window);
    }

    private MakeWindow() {

        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, WINDOW_WIDTH, WINDOW_HEIGHT);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                logWriter.close();
            }
        });
        dialogue = new JTextArea();
        dialogue.setEditable(true);
        JScrollPane scrollBar = new JScrollPane(dialogue);
        JPanel bp = new JPanel();
        bp.setLayout(new BoxLayout(bp, BoxLayout.X_AXIS));
        message = new JTextField();
        message.addActionListener(this);
        JButton enter = new JButton(BUTTON_ENTER);
        enter.addActionListener(this);
        bp.add(message);
        bp.add(enter);
        add(BorderLayout.CENTER, scrollBar);
        add(BorderLayout.SOUTH, bp);
        setVisible(true);
        try {
            logWriter = new PrintWriter(new FileWriter(NAME_LOG_FILE, true));
        } catch (IOException e) {
            dialogue.append(NO_LOG_FILE);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (message.getText().trim().length() > 0) {
            logWriter.printf("%s\n", message.getText());
            logWriter.flush();
        }
        message.setText("");
        message.requestFocusInWindow();
    }
    void appendDialogue(String message) {
        dialogue.append(message);
    }
    String getMessage() {
        if (message.getText().trim().length() > 0)
            return message.getText();
        return "";
    }
}