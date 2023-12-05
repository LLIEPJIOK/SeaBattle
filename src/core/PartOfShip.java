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

    public void paint(Graphics2D g2d) {
        int cellSize = Field.CELL_SIZE;
        int x = coordinate.getX() * cellSize;
        int y = coordinate.getY() * cellSize;
        g2d.setStroke(new BasicStroke(2.0f));
        g2d.setColor(Color.BLUE);
        g2d.drawRect(x, y, cellSize, cellSize);
    }

    public boolean update(Coordinate coordinate) {
        if (isAlive && this.coordinate.equals(coordinate)) {
            isAlive = false;
            return true;
        }
        return false;
    }
}
