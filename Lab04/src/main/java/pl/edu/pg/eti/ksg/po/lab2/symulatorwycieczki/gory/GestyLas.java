package pl.edu.pg.eti.ksg.po.lab2.symulatorwycieczki.gory;

import pl.edu.pg.eti.ksg.po.lab2.symulatorwycieczki.Wedrowka;

public class GestyLas extends Wedrowka {
    /**
     * @param odleglosc - długość wędrówki w GOTach
     */
    public GestyLas(double odleglosc) {
        super(odleglosc);
    }

    @Override
    public String getNazwa() {
        return GestyLas.class.getName();
    }

    @Override
    public double modyfikujPredkoscGrupy(double predkosc) {
        return .5;
    }

    @Override
    public int getTrudnoscNawigacyjna() {
        return 5;
    }
}
