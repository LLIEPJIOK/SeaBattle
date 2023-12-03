package core;

import dto.Coordinate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartOfShip {

    private Coordinate coordinate;
    private boolean isAlive;

    public PartOfShip(Coordinate coordinate) {
        this.coordinate = coordinate;
        this.isAlive = true;
    }

    public void paint(Graphics2D g2d, boolean isEnemy) {
        final int CELL_SIZE = 40;
        int x = coordinate.getX() * CELL_SIZE;
        int y = coordinate.getY() * CELL_SIZE;
        g2d.setStroke(new BasicStroke(2.0f));
        if (!isEnemy) {
            g2d.setColor(Color.BLUE);
            g2d.drawRect(x, y, CELL_SIZE, CELL_SIZE);
        }
    }

    public boolean update(Coordinate coordinate) {
        if (isAlive && this.coordinate.equals(coordinate)) {
            isAlive = false;
            return true;
        }
        return false;
    }
}
