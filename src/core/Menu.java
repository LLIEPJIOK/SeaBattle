package core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class Menu extends JFrame {

    private Clip backgroundMusic;
    private Clip buttonHoverSound;

    public Menu() {
        setTitle("Sea Battle");
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("MenuIcon.png"))).getImage());
        setSize(450, 330);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        // Создаем прозрачную панель
        TransparentPanel backgroundPanel = new TransparentPanel();
        backgroundPanel.setBounds(0, 0, 450, 330);
        add(backgroundPanel);

        loadBackgroundMusic("MenuTheme.wav");
        playBackgroundMusic();

        loadButtonHoverSound("ButtonSound.wav");

        // Создаем кнопки
        JButton playButton = new JButton("Play");
        JButton exitButton = new JButton("Exit");

        // Настройка кнопок
        configureButtonPlay(playButton);
        configureButtonExit(exitButton);

        backgroundPanel.add(playButton, 1, 1);
        backgroundPanel.add(exitButton, 1, 4);

        setVisible(true);
    }

    private void configureButtonPlay(JButton button) {
        button.setBackground(new Color(54, 126, 147, 0));
        button.setForeground(Color.WHITE);
        Border border = new LineBorder(new Color(0, 0, 0), 4, false);
        button.setBorder(border);
        Font font = new Font("Arial", Font.BOLD, 26);
        button.setFont(font);
        button.setForeground(new Color(0, 0, 0, 255));
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 50));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(Menu.this, "Начать игру");
            }
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playButtonHoverSound();
            }
        });
    }

    private void configureButtonExit(JButton button) {
        button.setBackground(new Color(173, 23, 41));
        button.setForeground(Color.WHITE);
        Border border = new LineBorder(new Color(0, 0, 0), 4, false);
        button.setBorder(border);
        Font font = new Font("Arial", Font.BOLD, 26);
        button.setFont(font);
        button.setForeground(new Color(173, 23, 41));
        button.setOpaque(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 50));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playButtonHoverSound();
            }
        });
    }

    // Класс для создания прозрачной панели
    private static class TransparentPanel extends JPanel {

        private Image backgroundImage;

        public TransparentPanel() {
            // Загружаем изображение из ресурсов
            backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("MenuAnimation.gif")))
                    .getImage();
            setLayout(new GridBagLayout());
        }

        public void add(Component comp, int gridx, int gridy) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = gridx;
            gbc.gridy = gridy;
            gbc.insets = new Insets(10, 0, 30, 0);
            super.add(comp, gbc);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            // Рисуем изображение
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void loadBackgroundMusic(String filePath) {
        try {
            InputStream audioSrc = getClass().getResourceAsStream(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(audioSrc));
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playBackgroundMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    private void loadButtonHoverSound(String filePath) {
        try {
            InputStream audioSrc = getClass().getResourceAsStream(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(audioSrc));
            buttonHoverSound = AudioSystem.getClip();
            buttonHoverSound.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playButtonHoverSound() {
        if (buttonHoverSound != null) {
            buttonHoverSound.setFramePosition(0); // Rewind to the beginning
            buttonHoverSound.start();
        }
    }

}
