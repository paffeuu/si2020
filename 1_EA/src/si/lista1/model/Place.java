package si.lista1.model;

public class Place {
    public final int id;
    public final double x;
    public final double y;

    public Place(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "x = " + x + " y = " + y;
    }
}
