package client;

import core.*;
import dto.MyActionEvent;
import server.Server;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import java.util.Objects;

import javax.swing.*;

public class ClientWindow extends JFrame implements ActionListener {

    private final JLabel playersTurnLabel;
    private final JLabel enemyNameLabel;

    public ClientWindow(String name, int port, boolean isHost) {
        setTitle("Sea Battle");
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource("../MenuIcon.png"))).getImage());
        setSize(590, 358);
        setLocationRelativeTo(null);
        setResizable(false);
        if (isHost) {
            Server server = new Server(port);
            server.start();
        }
        try {
            Socket socket = new Socket("localhost", port);
            MessageWriter messageWriter = new MessageWriter(socket);
            MessageReader messageReader = new MessageReader(socket);

            playersTurnLabel = ComponentsCreator.createLabel(isHost ? "Waiting another player..." : "Enemy turn");
            playersTurnLabel.setSize(600, 25);
            playersTurnLabel.setHorizontalAlignment(SwingConstants.CENTER);
            playersTurnLabel.setFont(new Font("Arial", Font.ITALIC, 21));

            JLabel myNameLabel = ComponentsCreator.createLabel(name);
            myNameLabel.setBounds(0, 25, 276, 20);

            enemyNameLabel = ComponentsCreator.createLabel("...");
            enemyNameLabel.setBounds(300, 25, 276, 20);
            enemyNameLabel.setHorizontalAlignment(SwingConstants.RIGHT);

            MyField myField = new MyField(messageWriter, isHost, playersTurnLabel);
            myField.setLocation(0, 45);
            EnemyField enemyField = new EnemyField(messageWriter, playersTurnLabel);
            enemyField.setLocation(300, 45);

            this.add(playersTurnLabel);
            this.add(myNameLabel);
            this.add(enemyNameLabel);
            this.add(myField);
            this.add(enemyField);
            this.add(new Panel());

            messageReader.addActionListener(myField);
            messageReader.addActionListener(enemyField);
            messageReader.addActionListener(this);
            messageReader.start();

            messageWriter.write(name);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event instanceof MyActionEvent myEvent) {
            if (myEvent.getData() instanceof String name) {
                enemyNameLabel.setText(name);
                if (playersTurnLabel.getText().equals("Waiting another player...")) {
                    playersTurnLabel.setText("Your turn");
                }
            }
        }
    }
}