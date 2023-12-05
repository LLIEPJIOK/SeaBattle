package core;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ComponentsCreator {

    public static JButton createButton(String name) {
        JButton button = new JButton(name);
        button.setBackground(new Color(0xB8CEE4));
        button.setForeground(Color.WHITE);
        Border border = new LineBorder(new Color(0, 0, 0), 4, false);
        button.setBorder(border);
        Font font = new Font("Arial", Font.BOLD, 26);
        button.setFont(font);
        button.setForeground(new Color(0, 0, 0, 255));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 50));
        button.addMouseListener(new ButtonHoverMouseAdapter());
        return button;
    }

    public static JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setBackground(new Color(0xB8CEE4));
        textField.setBorder(new LineBorder(new Color(0, 0, 0), 2, false));
        textField.setFont(new Font("Arial", Font.ITALIC, 21));
        textField.getDocument().addDocumentListener(new MyDocumentListener());
        return textField;
    }

    public static JTextArea createTextArea(String text) {
        JTextArea textArea = new JTextArea(text);
        textArea.setEditable(false);
        textArea.setFont(new Font("Arial", Font.BOLD, 16));
        textArea.setForeground(Color.BLACK);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBackground(Color.WHITE);
        textArea.setFocusable(false);
        return textArea;
    }

    public static JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setBackground(new Color(0xB8CEE4));
        label.setBorder(new LineBorder(new Color(0, 0, 0), 2, false));
        label.setFont(new Font("Arial", Font.ITALIC, 14));
        label.setVerticalAlignment(SwingConstants.CENTER);
        return label;
    }
}
