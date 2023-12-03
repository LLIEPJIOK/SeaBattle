import java.awt.*;
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
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Menu extends JFrame {

    //Some class private stuff
    private CardLayout partsPanels;
    private JPanel partPanel;
    private JTextField nameEnter;
    private Message help;
    private JButton playButton;
    private JButton exitButton;
    private JButton backButton;
    private JButton connectButton;
    private JButton createButton;
    private JButton helpButton;

    private JLabel nameLabel;

    private AnimatedPanel mainMenuPanel;
    private AnimatedPanel playPanel;

    //All music stuff
    private Clip backgroundMusic;
    private Clip buttonHoverSound;
    private Clip typeSound;

    //Menu constructor
    public Menu() {
        mainWindowCretion();
        allObjectsCreation();
        panelsCreation();
        musicCreation();
        helpDialogCreation();
        setVisible(true);
    }

    private void allObjectsCreation() {
        playButton = new JButton("Play");
        exitButton = new JButton("Exit");
        backButton = new JButton("Back");
        connectButton = new JButton("Connect");
        createButton = new JButton("Create");
        helpButton = new JButton("Help");
        nameEnter = new JTextField();
        nameLabel = new JLabel("Enter name:");

        configureButtonPlay(playButton);
        configureButtonExit(exitButton);
        configureButtonBack(backButton);
        configureButtonCreate(createButton);
        configureButtonConnect(connectButton);
        configureButtonHelp(helpButton);
        configureNameEnterField(nameEnter);
        configureNameLabel(nameLabel);
    }

    private void panelsCreation() {
        partsPanels = new CardLayout();
        partPanel = new JPanel(partsPanels);

        mainMenuPanel = new AnimatedPanel();
        mainMenuPanel.setBounds(0, 0, 450, 330);

        playPanel = new AnimatedPanel();
        playPanel.setBounds(0, 0, 450, 330);

        playPanel.add(nameEnter, 1, 0);
        playPanel.add(nameLabel, 0, 0);
        playPanel.add(connectButton, 0, 1);
        playPanel.add(createButton, 1, 1);
        playPanel.add(backButton, 0, 2);
        playPanel.add(helpButton, 1, 2);


        mainMenuPanel.add(playButton, 1, 1);
        mainMenuPanel.add(exitButton, 1, 4);
        partPanel.add(mainMenuPanel, "start");
        partPanel.add(playPanel, "game");

        partsPanels.show(partPanel, "start");
        this.getContentPane().add(partPanel);
    }

    private void musicCreation() {
        loadBackgroundMusic("MenuTheme.wav");
        loadButtonHoverSound("ButtonSound.wav");
        loadTypeSound("TypeSound.wav");
        playBackgroundMusic();
    }

    private void mainWindowCretion() {
        setTitle("Sea Battle");
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("MenuIcon.png"))).getImage());
        setSize(450, 330);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
    }

    private void helpDialogCreation() {
        help = new Message(" -Press 'connect' if your friend already created a game to link with him and start to play\n -Press 'create' to make your own host\n -Don't foget to enter your name \n -Good luck!");
    }

    private void createButton(JButton button) {
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

    private void configureButtonHelp(JButton button) {
        createButton(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                help.setVisible(true);
            }
        });
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playButtonHoverSound();
            }
        });
    }

    private void configureButtonCreate(JButton button) {
        createButton(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playButtonHoverSound();
            }
        });
    }

    private void configureButtonConnect(JButton button) {
        createButton(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        });
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playButtonHoverSound();
            }
        });
    }

    private void configureButtonPlay(JButton button) {
        createButton(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                partsPanels.show(partPanel, "game");
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
        createButton(button);
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

    private void configureButtonBack(JButton button) {
        createButton(button);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameEnter.setText("");
                partsPanels.show(partPanel, "start");

            }
        });

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                playButtonHoverSound();
            }
        });
    }

    private void configureNameEnterField(JTextField textField) {
        textField.setPreferredSize(new Dimension(200, 36));
        textField.setBackground(new Color(0xB8CEE4));
        Border border = new LineBorder(new Color(0, 0, 0), 2, false);
        textField.setBorder(border);
        Font font = new Font("Arial", Font.ITALIC, 21);
        textField.setFont(font);
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                playTypeSound();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });
    }

    private void configureNameLabel(JLabel label) {
        Font font = new Font("Arial", Font.BOLD, 26);
        label.setFont(font);
    }

    private static class AnimatedPanel extends JPanel {

        private Image backgroundImage;

        public AnimatedPanel() {
            backgroundImage = new ImageIcon(Objects.requireNonNull(getClass().getResource("MenuAnimation.gif")))
                    .getImage();
            setLayout(new GridBagLayout());
        }

        public void add(Component comp, int gridx, int gridy) {
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = gridx;
            gbc.gridy = gridy;
            gbc.insets = new Insets(10, 20, 30, 0);
            super.add(comp, gbc);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private class Message extends JDialog {
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
            configuretextArea(textArea, text);

            JButton okButton = new JButton("Close");
            configureButton(okButton);

            add(textArea, BorderLayout.CENTER);
            add(okButton, BorderLayout.SOUTH);

            setVisible(false);
        }

        private void configuretextArea(JTextArea textArea, String text) {
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
            createButton(okButton);
            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            okButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    playButtonHoverSound();
                }
            });
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
            buttonHoverSound.setFramePosition(0);
            buttonHoverSound.start();
        }
    }

    private void loadTypeSound(String filePath) {
        try {
            InputStream audioSrc = getClass().getResourceAsStream(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(audioSrc));
            typeSound = AudioSystem.getClip();
            typeSound.open(audioStream);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private void playTypeSound() {
        if (typeSound != null) {
            typeSound.setFramePosition(80);
            typeSound.start();
        }
    }
}
