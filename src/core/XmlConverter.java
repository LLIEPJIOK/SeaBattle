package core;

import dto.Message;
import jakarta.xml.bind.*;

import java.io.*;

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
