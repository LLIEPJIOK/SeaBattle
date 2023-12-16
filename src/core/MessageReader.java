package core;

import dto.Message;
import dto.MyActionEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MessageReader extends Thread {
    private final DataInputStream in;
    private final List<ActionListener> listeners;
    private boolean isFinishing;

    private final XmlConverter xmlConverter = new XmlConverter();

    public MessageReader(Socket socket) {
        isFinishing = false;
        listeners = new ArrayList<>();
        try {
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public void read() {
        try {
            while (!isFinishing) {
                if (in.available() > 0) {
                    int length = in.readInt();
                    String name = in.readUTF();
                    ByteArrayInputStream byteIn = new ByteArrayInputStream(in.readNBytes(length));
                    Message message = xmlConverter.fromXml((Class<? extends Message>) Class.forName(name), byteIn);
                    ActionListener listener = null;
                    switch (name) {
                        case "dto.Request" -> listener = listeners.getFirst();
                        case "dto.Response" -> listener = listeners.get(listeners.size() == 1 ? 0 : 1);
                        case "dto.NameMessage" -> listener = listeners.getLast();
                    }
                    assert listener != null;
                    listener.actionPerformed(new MyActionEvent(
                            this, ActionEvent.ACTION_PERFORMED,
                            "read name", message));
                }
            }
        } catch (Exception e) {
            // TODO: handle error
            throw new RuntimeException(e);
        }
    }

    public void addActionListener(ActionListener listener) {
        listeners.add(listener);
    }

    public void stopReader() {
        isFinishing = true;
    }

    @Override
    public void run() {
        read();
    }
}
