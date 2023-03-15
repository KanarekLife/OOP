package com.nieradko.po.lab1.transformacje;
public class Skalowanie implements Transformacja{
    private final double skalaX, skalaY;

    public Skalowanie(double skalaX, double skalaY) {
        this.skalaX = skalaX;
        this.skalaY = skalaY;
    }

    public double getSkalaX() {
        return skalaX;
    }

    public double getSkalaY() {
        return skalaY;
    }

    @Override
    public Punkt transformuj(Punkt p) {
        return new Punkt(skalaX * p.getX(), skalaY * p.getY());
    }

    @Override
    public Transformacja getTransformacjaOdwrotna() throws BrakTransformacjiOdwrotnejException {
        if (skalaY == 0 || skalaX == 0) {
            throw new BrakTransformacjiOdwrotnejException("Brak transformacji odwrotnej. Przynajmniej jeden z czynników skalowania jest równy 0");
        }

        return new Skalowanie(1/skalaX, 1/skalaY);
    }

    @Override
    public String toString() {
        return String.format("Skalowanie .2%f x oraz .2%f y", skalaX, skalaY);
    }
}
