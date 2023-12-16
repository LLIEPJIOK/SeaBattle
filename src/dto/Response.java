package dto;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Response extends Message {

    @XmlElement
    private Coordinate coordinate;

    @XmlElement
    private boolean isHit;

    @XmlElementWrapper(name="coordinates")
    @XmlElement(name="coordinate")
    private List<Coordinate> coordinates;

    public Response(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.isHit = false;
        this.coordinates = new ArrayList<>();
        this.messageType = MessageType.RESPONSE;
    }
}
