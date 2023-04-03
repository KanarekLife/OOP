package com.nieradko.po.lab1.transformacje;
public class Translacja implements Transformacja {
    private final double dX, dY;

    public Translacja(double dX, double dY) {
        this.dX = dX;
        this.dY = dY;
    }

    public double getdX() {
        return dX;
    }

    public double getdY() {
        return dY;
    }

    @Override
    public Punkt transformuj(Punkt p) {
        return new Punkt(p.getX() + dX, p.getY() + dY);
    }

    @Override
    public Transformacja getTransformacjaOdwrotna() throws BrakTransformacjiOdwrotnejException {
        return new Translacja(-dX, -dY);
    }

    @Override
    public String toString() {
        return String.format("Translacja o wektor (.2%f, .2%f)", dX, dY);
    }
}
