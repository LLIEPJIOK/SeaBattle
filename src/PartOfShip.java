public class PartOfShip {
    private Point point;
    transient private boolean isAlive;
    public PartOfShip(Point point) {
        this.point = point;
        this.isAlive = true;
    }
}
