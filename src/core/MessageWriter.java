package core;

import dto.Message;

import java.io.*;
import java.net.Socket;

public class MessageWriter {
    private final DataOutputStream dataOut;
    private final XmlConverter xmlConverter = new XmlConverter();

    public MessageWriter(Socket socket) {
        try {
            dataOut = new DataOutputStream(socket.getOutputStream());
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
            dataOut.writeInt(res.length);
            dataOut.writeUTF(message.getClass().getName());
            dataOut.write(res);
            dataOut.flush();
        } catch (Exception e) {
            // TODO: handle error
            throw new RuntimeException(e);
        }
    }
}
