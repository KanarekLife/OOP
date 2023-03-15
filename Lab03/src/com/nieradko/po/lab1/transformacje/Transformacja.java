package com.nieradko.po.lab1.transformacje;
public interface Transformacja {
    Punkt transformuj(Punkt p);
    Transformacja getTransformacjaOdwrotna() throws BrakTransformacjiOdwrotnejException;
}
