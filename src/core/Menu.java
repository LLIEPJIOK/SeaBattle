package core;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class Menu extends JFrame {

    //Some class stuff
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
        SoundPlayer.loadBackgroundMusic("MenuTheme.wav");
        SoundPlayer.loadButtonHoverSound("ButtonSound.wav");
        SoundPlayer.loadTypeSound("TypeSound.wav");

        SoundPlayer.playBackgroundMusic();
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

    private void configureButtonHelp(JButton button) {
        ComponentsCreator.createButton(button);
        button.addActionListener(e -> help.setVisible(true));
        button.addMouseListener(new ButtonHoverMouseAdapter());
    }

    private void configureButtonCreate(JButton button) {
        ComponentsCreator.createButton(button);
        button.addActionListener(e -> {});
        button.addMouseListener(new ButtonHoverMouseAdapter());
    }

    private void configureButtonConnect(JButton button) {
        ComponentsCreator.createButton(button);
        button.addActionListener(e -> {});
        button.addMouseListener(new ButtonHoverMouseAdapter());
    }

    private void configureButtonPlay(JButton button) {
        ComponentsCreator.createButton(button);
        button.addActionListener(e -> partsPanels.show(partPanel, "game"));
        button.addMouseListener(new ButtonHoverMouseAdapter());
    }

    private void configureButtonExit(JButton button) {
        ComponentsCreator.createButton(button);
        button.addActionListener(e -> System.exit(0));
        button.addMouseListener(new ButtonHoverMouseAdapter());
    }

    private void configureButtonBack(JButton button) {
        ComponentsCreator.createButton(button);
        button.addActionListener(e -> {
            nameEnter.setText("");
            partsPanels.show(partPanel, "start");
        });

        button.addMouseListener(new ButtonHoverMouseAdapter());
    }

    private void configureNameEnterField(JTextField textField) {
        textField.setPreferredSize(new Dimension(200, 36));
        textField.setBackground(new Color(0xB8CEE4));
        Border border = new LineBorder(new Color(0, 0, 0), 2, false);
        textField.setBorder(border);
        Font font = new Font("Arial", Font.ITALIC, 21);
        textField.setFont(font);
        textField.getDocument().addDocumentListener(new MyDocumentListener());
    }

    private void configureNameLabel(JLabel label) {
        Font font = new Font("Arial", Font.BOLD, 26);
        label.setFont(font);
    }
}
