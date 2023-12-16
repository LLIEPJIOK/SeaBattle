package dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinate {

    @XmlElement
    private int x;

    @XmlElement
    private int y;
}
