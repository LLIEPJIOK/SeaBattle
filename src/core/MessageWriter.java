package core;

import com.google.gson.Gson;
import dto.Response;

import java.io.*;
import java.net.Socket;

public class MessageWriter {
    private final PrintWriter writer;

    public MessageWriter(Socket socket) {
        try {
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void write(Object obj) {
        int type = 0;
        if (obj instanceof Response) {
            type = 1;
        }
        Gson gson = new Gson();
        writer.write(type + "\n" + gson.toJson(obj) + "\n");
        writer.flush();
    }
}
