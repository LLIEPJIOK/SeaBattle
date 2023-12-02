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
    transient private ShipInformer shipInformer;

    public Ship(List<PartOfShip> parts) {
        this.partsAlive = parts.size();
        this.parts = parts;
    }

    public void paint(Graphics2D g2d, boolean isEnemy) {
        for (PartOfShip part : parts) {
            part.paint(g2d, isEnemy);
        }
    }

    public boolean update(Coordinate coordinate) {
        for (PartOfShip part : parts) {
            if (part.update(coordinate)) {
                --partsAlive;
                if (partsAlive == 0) {
                    shipInformer.informAboutDestruction(parts.stream().map(PartOfShip::getCoordinate).toList());
                }
                return true;
            }
        }
        return false;
    }
}
