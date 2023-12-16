package core;

import dto.Message;
import dto.MyActionEvent;
import org.apache.xerces.parsers.DOMParser;
import org.apache.xerces.xni.parser.XMLInputSource;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessageReader extends Thread {
    private final DataInputStream dataIn;
    private final List<ActionListener> listeners;
    private boolean isFinishing;

    private final XmlConverter xmlConverter = new XmlConverter();

    public MessageReader(Socket socket) {
        isFinishing = false;
        listeners = new ArrayList<>();
        try {
            dataIn = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateXmlScheme(InputStream in, String path) {
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File(path));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(in));
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private boolean validateDtd(InputStream in, String path) {
        try {
            DOMParser parser = new DOMParser();
            parser.setEntityResolver(new DtdEntityResolver(new File(path)));
            parser.parse(new XMLInputSource(null, null, null, in, null));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean validate(byte[] buf, String xmlSchemeName, String dtdName) {
        return validateXmlScheme(new ByteArrayInputStream(buf), xmlSchemeName) &&
                validateDtd(new ByteArrayInputStream(buf), dtdName);
    }

    @SuppressWarnings("unchecked")
    public void read() {
        try {
            while (!isFinishing) {
                if (dataIn.available() > 0) {
                    int length = dataIn.readInt();
                    String name = dataIn.readUTF();
                    byte[] buf = dataIn.readNBytes(length);
                    ActionListener listener;
                    String xmlSchemeName;
                    String dtdName;
                    switch (name) {
                        case "dto.Request" -> {
                            xmlSchemeName = Objects.requireNonNull(getClass().getResource("XmlSchemes/RequestScheme.xml")).getPath();
                            dtdName = Objects.requireNonNull(getClass().getResource("Dtds/Request.dtd")).getPath();
                            listener = listeners.getFirst();
                        }
                        case "dto.Response" -> {
                            xmlSchemeName = Objects.requireNonNull(getClass().getResource("XmlSchemes/ResponseScheme.xml")).getPath();
                            dtdName = Objects.requireNonNull(getClass().getResource("Dtds/Response.dtd")).getPath();
                            listener = listeners.get(listeners.size() == 1 ? 0 : 1);
                        }
                        case "dto.NameMessage" -> {
                            xmlSchemeName = Objects.requireNonNull(getClass().getResource("XmlSchemes/NameMessageScheme.xml")).getPath();
                            dtdName = Objects.requireNonNull(getClass().getResource("Dtds/NameMessage.dtd")).getPath();
                            listener = listeners.getLast();
                        }
                        // TODO: handle error
                        default -> throw new RuntimeException("unknown data");
                    }
                    if (validate(buf, xmlSchemeName, dtdName)) {
                        Message message = xmlConverter.fromXml((Class<? extends Message>) Class.forName(name), new ByteArrayInputStream(buf));
                        listener.actionPerformed(new MyActionEvent(
                                this, ActionEvent.ACTION_PERFORMED,
                                "read name", message));
                    }
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
