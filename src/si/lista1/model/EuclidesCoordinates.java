package si.lista1.model;

public class EuclidesCoordinates {
    public final double x;
    public final double y;

    public EuclidesCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "x = " + x + " y = " + y;
    }
}
