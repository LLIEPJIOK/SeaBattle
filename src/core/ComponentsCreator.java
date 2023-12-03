package core;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ComponentsCreator {

    public static void createButton(JButton button) {
        button.setBackground(new Color(0xB8CEE4));
        button.setForeground(Color.WHITE);
        Border border = new LineBorder(new Color(0, 0, 0), 4, false);
        button.setBorder(border);
        Font font = new Font("Arial", Font.BOLD, 26);
        button.setFont(font);
        button.setForeground(new Color(0, 0, 0, 255));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 50));
    }
}
