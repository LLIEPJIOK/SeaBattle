package core;

import dto.Coordinate;
import dto.Response;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Ship {
    private int partsAlive;
    private List<PartOfShip> parts;

    public Ship(List<PartOfShip> parts) {
        this.partsAlive = parts.size();
        this.parts = parts;
    }

    public void paint(Graphics2D g2d) {
        for (PartOfShip part : parts) {
            part.paint(g2d);
        }
    }

    public Response update(Coordinate coordinate) {
        for (PartOfShip part : parts) {
            if (part.update(coordinate)) {
                --partsAlive;
                Response response = new Response(coordinate);
                response.setHit(true);
                if (partsAlive == 0) {
                    response.setCoordinates(parts.stream().map(PartOfShip::getCoordinate).toList());
                }
                return response;
            }
        }
        return null;
    }
}
