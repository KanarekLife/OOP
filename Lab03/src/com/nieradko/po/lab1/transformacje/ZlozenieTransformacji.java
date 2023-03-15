package com.nieradko.po.lab1.transformacje;

import java.util.stream.IntStream;

public class ZlozenieTransformacji implements Transformacja{
    private final Transformacja[] transformacje;

    public ZlozenieTransformacji(Transformacja[] transformacje) {
        this.transformacje = transformacje;
    }

    @Override
    public Punkt transformuj(Punkt p) {
        for (Transformacja t : transformacje) {
            p = t.transformuj(p);
        }
        return p;
    }

    @Override
    public Transformacja getTransformacjaOdwrotna() throws BrakTransformacjiOdwrotnejException {
        try {
            return new ZlozenieTransformacji(IntStream.range(0, transformacje.length).mapToObj(i -> {
                try {
                    return transformacje[transformacje.length - i - 1].getTransformacjaOdwrotna();
                } catch (BrakTransformacjiOdwrotnejException e) {
                    throw new RuntimeException(e);
                }
            }).toArray(Transformacja[]::new));
        } catch(RuntimeException e) {
            throw new BrakTransformacjiOdwrotnejException();
        }
    }
}
