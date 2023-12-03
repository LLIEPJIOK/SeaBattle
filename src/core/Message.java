package core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class Message extends JDialog {
    Message(String text) {
        setTitle("How to start");
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("HelpIcon.png"))).getImage());
        setSize(485, 200);
        setLocationRelativeTo(null);

        JTextArea textArea = new JTextArea(text);
        configureTextArea(textArea, text);

        JButton okButton = new JButton("Close");
        configureButton(okButton);

        add(textArea, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);

        setVisible(false);
    }

    private void configureTextArea(JTextArea textArea, String text) {
        textArea.setEditable(false);
        Font font = new Font("Arial", Font.BOLD, 16);
        textArea.setFont(font);
        textArea.setForeground(Color.BLACK);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Color.WHITE);
        textArea.setFocusable(false);
    }

    private void configureButton(JButton okButton) {
        ComponentsCreator.createButton(okButton);
        okButton.addActionListener(e -> dispose());
        okButton.addMouseListener(new ButtonHoverMouseAdapter());
    }
}