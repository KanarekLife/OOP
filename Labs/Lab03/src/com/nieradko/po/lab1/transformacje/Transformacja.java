package com.nieradko.po.lab1.transformacje;
public interface Transformacja {
    Punkt transformuj(Punkt p);
    default Trojkat transformuj(Trojkat t) throws NiewlasciwyTrojkatException {
        return new Trojkat(
                transformuj(t.p1),
                transformuj(t.p2),
                transformuj(t.p3)
        );
    };
    Transformacja getTransformacjaOdwrotna() throws BrakTransformacjiOdwrotnejException;
}
