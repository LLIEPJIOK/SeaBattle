import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Menu extends JFrame {

    public Menu() {
        menuCreation();
    }

    private void menuCreation() {
        setTitle("Sea Battle");
        setSize(200, 150);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

        ImageIcon gifIcon = new ImageIcon("resources/MenuAnimation.gif");
        JLabel backgroundLabel = new JLabel(gifIcon);
        backgroundLabel.setBounds(0, 0, 200, 150);
        add(backgroundLabel);

        JButton playButton = new JButton("Play");
        playButton.setBackground(new Color(92, 184, 92));
        playButton.setForeground(Color.WHITE);
        Border border = new LineBorder(new Color(76, 174, 76), 2, true);
        playButton.setBorder(border);
        Font font = new Font("Arial", Font.BOLD, 14);
        playButton.setFont(font);
        playButton.setFocusPainted(false);
        playButton.setBounds(50, 20, 100, 30);

        JButton exitButton = new JButton("Выйти");
        exitButton.setBackground(new Color(92, 184, 92));
        exitButton.setForeground(Color.WHITE);
        exitButton.setBorder(border);
        exitButton.setFont(font);
        exitButton.setFocusPainted(false);
        exitButton.setBounds(50, 60, 100, 30);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Menu.this, "Начать игру");
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        add(playButton);
        add(exitButton);

        setVisible(true);
    }
}
