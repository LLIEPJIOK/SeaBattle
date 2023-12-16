package dto;

import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public abstract class Message implements Serializable {
    @XmlAttribute
    protected MessageType messageType;
}
