package client;

import core.MessageReader;
import core.MessageWriter;
import core.MyField;
import server.Server;

import java.awt.Dimension;
import java.awt.Panel;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;

public class ClientWindow extends JFrame {

    public static boolean canAttack;

    public ClientWindow(int port, boolean isHost) {
        if (isHost) {
            Server server = new Server(port);
            server.start();
        }
        try {
            Socket socket = new Socket("localhost", port);
            MessageWriter messageWriter = new MessageWriter(socket);
            MessageReader messageReader = new MessageReader(socket);

            canAttack = isHost;
            MyField myField1 = new MyField(messageWriter, false, isHost);
            myField1.setLocation(0, 0);
            MyField myField2 = new MyField(messageWriter, true, isHost);
            myField2.setLocation(500, 0);
            add(myField1);
            add(myField2);
            add(new Panel());

            messageReader.addActionListener(myField1);
            messageReader.addActionListener(myField2);
            messageReader.start();

            this.setSize(new Dimension(1000, 500));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}