package core;

import dto.Message;

import java.io.*;
import java.net.Socket;

public class MessageWriter {
    private final DataOutputStream out;
    private final XmlConverter xmlConverter = new XmlConverter();

    public MessageWriter(Socket socket) {
        try {
            out = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            // TODO: handle error
            throw new RuntimeException(e);
        }
    }

    public void write(Message message) {
        try (ByteArrayOutputStream byteOut = new ByteArrayOutputStream(512);
             DataOutputStream bufOut = new DataOutputStream(byteOut)) {

            xmlConverter.toXml(message, bufOut);
            bufOut.flush();

            byte[] res = byteOut.toByteArray();
            out.writeInt(res.length);
            out.writeUTF(message.getClass().getName());
            out.write(res);
            out.flush();
        } catch (Exception e) {
            // TODO: handle error
            throw new RuntimeException(e);
        }
    }
}
