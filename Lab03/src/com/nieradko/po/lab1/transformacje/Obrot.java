package com.nieradko.po.lab1.transformacje;

public class Obrot implements Transformacja {
    private final double angle;

    public Obrot(double angle) {
        this.angle = angle;
    }

    @Override
    public Punkt transformuj(Punkt p) {
        return new Punkt(
                p.getX() * Math.cos(Math.toRadians(angle)) - p.getY() * Math.sin(Math.toRadians(angle)),
                p.getX() * Math.sin(Math.toRadians(angle)) + p.getY() * Math.cos(Math.toRadians(angle))
        );
    }

    @Override
    public Transformacja getTransformacjaOdwrotna() {
        return new Obrot(-angle);
    }
}
