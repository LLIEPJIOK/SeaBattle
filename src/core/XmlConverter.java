package core;

import dto.Message;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class XmlConverter implements Serializable {

    public void toXml(Message msg, OutputStream out) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(msg.getClass());
        Marshaller marshaller = context.createMarshaller();
        marshaller.marshal(msg, out);
    }

    public Message fromXml(Class<? extends Message> message, InputStream in) throws Exception {
        JAXBContext context = JAXBContext.newInstance(message);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (Message) unmarshaller.unmarshal(in);
    }
}
