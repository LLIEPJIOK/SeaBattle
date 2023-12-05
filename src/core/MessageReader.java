package core;

import com.google.gson.Gson;
import dto.MyActionEvent;
import dto.Request;
import dto.Response;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class MessageReader extends Thread {
    BufferedReader reader;
    private final List<ActionListener> listeners;
    private boolean isFinishing;

    public MessageReader(Socket socket) {
        isFinishing = false;
        listeners = new ArrayList<>();
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void read() {
        while (!isFinishing) {
            try {
                String ans;
                if ((ans = reader.readLine()) != null) {
                    Gson gson = new Gson();
                    switch (ans) {
                        case "request" -> listeners.get(0).actionPerformed(new MyActionEvent(
                                this, ActionEvent.ACTION_PERFORMED,
                                "read request", gson.fromJson(reader.readLine(), Request.class)));
                        case "response" -> listeners.get(listeners.size() == 1 ? 0 : 1).actionPerformed(new MyActionEvent(
                                this, ActionEvent.ACTION_PERFORMED,
                                "read response", gson.fromJson(reader.readLine(), Response.class)));
                        case "name" -> listeners.getLast().actionPerformed(new MyActionEvent(
                                this, ActionEvent.ACTION_PERFORMED,
                                "read name", gson.fromJson(reader.readLine(), String.class)));
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
