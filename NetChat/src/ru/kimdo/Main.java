package ru.kimdo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Pavel Petrikovskiy
 * @version 17.06.17
 */

public class Main extends JFrame implements ActionListener {
    private JTextArea dialogue;
    private JTextField message;

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
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (message.getText().trim().length() > 0) {
            dialogue.append(message.getText() + "\n");
            dialogue.append("Тестовый запуск!\n");
        }
        message.setText("");
        message.requestFocusInWindow();
    }
}
