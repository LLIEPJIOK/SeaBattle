package core;

import com.google.gson.Gson;
import dto.Request;
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
        switch (obj.getClass().getSimpleName()) {
            case "Response" -> writer.write("response\n");
            case "Request" -> writer.write("request\n");
            case "String" -> writer.write("name\n");
        }
        Gson gson = new Gson();
        writer.write(gson.toJson(obj) + "\n");
        writer.flush();
    }
}
