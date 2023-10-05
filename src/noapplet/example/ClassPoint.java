package noapplet.example;

import java.awt.*;

public class ClassPoint extends Point {
    private Color color;

    public int hashcode() {
        return this.x * 100 + this.y;
    }
}