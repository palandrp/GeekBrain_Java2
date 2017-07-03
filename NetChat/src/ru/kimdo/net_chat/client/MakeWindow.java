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
    private PrintWriter pw;

    public static void main(String[] args) {
        new MakeWindow();
    }

    private MakeWindow() {

        setTitle(TITLE_OF_PROGRAM);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setBounds(START_LOCATION, START_LOCATION, WINDOW_WIDTH, WINDOW_HEIGHT);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                pw.close();
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
            pw = new PrintWriter(new FileWriter(NAME_LOG_FILE, true));
        } catch (IOException e) {
            dialogue.append(NO_LOG_FILE);
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (message.getText().trim().length() > 0) {
            dialogue.append(message.getText() + "\n");
            dialogue.append("Тестовый запуск!\n");
            pw.printf("%s\n", message.getText());
            pw.print("Тестовый запуск!\n");
            pw.flush();
        }
        message.setText("");
        message.requestFocusInWindow();
    }
}