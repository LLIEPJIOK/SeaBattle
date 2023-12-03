package server;

import core.MessageReader;
import core.MessageWriter;
import dto.MyActionEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server extends Thread implements ActionListener {
    private boolean isFinishing;
    private final int port;

    MessageWriter firstMessageWriter;
    MessageReader firstMessageReader;
    MessageWriter secondMessageWriter;
    MessageReader secondMessageReader;

    public Server(int port) {
        this.port = port;
        isFinishing = false;
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            Socket firstClientSocket = serverSocket.accept();
            firstMessageWriter = new MessageWriter(firstClientSocket);
            firstMessageReader = new MessageReader(firstClientSocket);

            Socket secondClientSocket = serverSocket.accept();
            secondMessageWriter = new MessageWriter(secondClientSocket);
            secondMessageReader = new MessageReader(secondClientSocket);

            firstMessageReader.addActionListener(this);
            firstMessageReader.addActionListener(this);
            secondMessageReader.addActionListener(this);
            secondMessageReader.addActionListener(this);

            firstMessageReader.start();
            secondMessageReader.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopServer() {
        isFinishing = true;
    }

    @Override
    public void run() {
        startServer();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event instanceof MyActionEvent myEvent) {
            if (myEvent.getSource() == firstMessageReader) {
                secondMessageWriter.write(myEvent.getData());
            } else if (myEvent.getSource() == secondMessageReader) {
                firstMessageWriter.write(myEvent.getData());
            }
        }
    }
}
