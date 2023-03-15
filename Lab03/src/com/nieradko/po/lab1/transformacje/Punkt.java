package com.nieradko.po.lab1.transformacje;
public class Punkt {
    private final double x, y;

    public Punkt(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Punkt p)) {
            return false;
        }
        return p.x == x && p.y == y;
    }

    @Override
    public int hashCode() {
        return 21 * Double.hashCode(x) * Double.hashCode(y) + 37;
    }

    @Override
    public String toString() {
        return String.format("(%f, %f)", x, y);
    }

    public static final Punkt O = new Punkt(0,0);
    public static final Punkt E_X = new Punkt(1, 0);
    public static final Punkt E_Y = new Punkt(0, 1);
}
