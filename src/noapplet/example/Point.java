package noapplet.example;

public class Point {
    int x, y;

    public Point() {
        // Reason: Superclass might have different behavior
        this(0, 0);
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean equals(Object that) {
        // The perfect equals() method
        if (this == that) return true;
        if (that == null) return false;
        if (this.getClass() != that.getClass()) return false;
        return true;
    }

    public int hashCode() {
        // TODO
        // for each field compute and combine the hash code
        return x << 8 | y; // TODO
    }

    @Override
    public String toString() {
        return String.format("Point [x=%d, y=%d]", x, y);
    }


}
