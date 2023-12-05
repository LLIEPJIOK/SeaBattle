package client;

import core.*;
import server.Server;

import java.awt.Dimension;
import java.awt.Panel;
import java.io.IOException;
import java.net.Socket;

import javax.swing.*;

public class ClientWindow extends JFrame {

    public ClientWindow(int port, boolean isHost) {
        if (isHost) {
            Server server = new Server(port);
            server.start();
        }
        try {
            Socket socket = new Socket("localhost", port);
            MessageWriter messageWriter = new MessageWriter(socket);
            MessageReader messageReader = new MessageReader(socket);

            JLabel playersTurnLabel = ComponentsCreator.createLabel(isHost ? "Your turn" : "Enemy turn");
            playersTurnLabel.setSize(600, 20);

            MyField myField = new MyField(messageWriter, isHost, playersTurnLabel);
            myField.setLocation(5, 20);
            EnemyField enemyField = new EnemyField(messageWriter, playersTurnLabel);
            enemyField.setLocation(305, 20);

            this.add(playersTurnLabel);
            this.add(myField);
            this.add(enemyField);
            this.add(new Panel());

            messageReader.addActionListener(myField);
            messageReader.addActionListener(enemyField);
            messageReader.start();

            this.setSize(new Dimension(600, 340));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}