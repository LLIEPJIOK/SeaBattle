package core;

import client.ClientWindow;
import server.Server;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;
import javax.swing.*;

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

    //Menu constructor
    public Menu() {
        mainWindowCreation();
        allObjectsCreation();
        panelsCreation();
        musicCreation();
        helpDialogCreation();
    }

    private void mainWindowCreation() {
        setTitle("Sea Battle");
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("MenuIcon.png"))).getImage());
        setSize(450, 330);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
    }

    private void allObjectsCreation() {
        createButtonPlay();
        createButtonExit();
        createButtonBack();
        createButtonConnect();
        createButtonCreate();
        createButtonHelp();
        createNameEnterField();
        createNameLabel();
    }

    private void panelsCreation() {
        partsPanels = new CardLayout();
        partPanel = new JPanel(partsPanels);

        AnimatedPanel mainMenuPanel = new AnimatedPanel();
        mainMenuPanel.setBounds(0, 0, 450, 330);

        AnimatedPanel playPanel = new AnimatedPanel();
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

    private void helpDialogCreation() {
        help = new Message(" -Press 'connect' if your friend already created a game to link with him and start to play\n -Press 'create' to make your own host\n -Don't foget to enter your name \n -Good luck!");
    }

    private void createButtonPlay() {
        playButton = ComponentsCreator.createButton("Play");
        playButton.addActionListener(e -> partsPanels.show(partPanel, "game"));
    }

    private void createButtonExit() {
        exitButton = ComponentsCreator.createButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));
    }

    private void createButtonBack() {
        backButton = ComponentsCreator.createButton("Back");
        backButton.addActionListener(e -> partsPanels.show(partPanel, "start"));
    }

    private void createButtonConnect() {
        connectButton = ComponentsCreator.createButton("Connect");
        connectButton.addActionListener(e -> {
            this.setVisible(false);
            ClientWindow clientWindow = new ClientWindow(8080, false);
            clientWindow.setVisible(true);
        });
    }

    private void createButtonCreate() {
        createButton = ComponentsCreator.createButton("Create");
        createButton.addActionListener(e -> {
            this.setVisible(false);
            ClientWindow clientWindow = new ClientWindow(8080, true);
            clientWindow.setVisible(true);
        });
    }

    private void createButtonHelp() {
        helpButton = ComponentsCreator.createButton("Help");
        helpButton.addActionListener(e -> help.setVisible(true));
    }

    private void createNameEnterField() {
        nameEnter = ComponentsCreator.createTextField();
        nameEnter.setPreferredSize(new Dimension(200, 36));
    }

    private void createNameLabel() {
        nameLabel = new JLabel("Enter name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, 26));
    }
}
