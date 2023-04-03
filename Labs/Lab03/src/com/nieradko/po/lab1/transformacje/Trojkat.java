package com.nieradko.po.lab1.transformacje;

public class Trojkat {
    public final Punkt p1, p2, p3;

    public Trojkat(Punkt p1, Punkt p2, Punkt p3) throws NiewlasciwyTrojkatException {
        if (p1.equals(p2) || p2.equals(p3) || p3.equals(p1)) {
            throw new NiewlasciwyTrojkatException("Dwa z trzech punktów trojkąta są identyczne.");
        }

        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Trojkat t)) {
            return false;
        }

        return p1.equals(t.p1) && p2.equals(t.p2) && p3.equals(t.p3);
    }


    @Override
    public String toString() {
        return "Trojkat{" + p1.toString() + ", " + p2.toString() + ", " + p3.toString() + "}";
    }
}
