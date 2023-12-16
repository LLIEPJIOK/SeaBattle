package core;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class HelpWindow extends JDialog {
    HelpWindow(String text) {
        setTitle("How to start");
        setModal(true);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("HelpIcon.png"))).getImage());
        setSize(485, 200);
        setLocationRelativeTo(null);

        JTextArea textArea = ComponentsCreator.createTextArea(text);

        JButton okButton = ComponentsCreator.createButton("Close");
        okButton.addActionListener(e -> dispose());

        add(textArea, BorderLayout.CENTER);
        add(okButton, BorderLayout.SOUTH);
    }
}