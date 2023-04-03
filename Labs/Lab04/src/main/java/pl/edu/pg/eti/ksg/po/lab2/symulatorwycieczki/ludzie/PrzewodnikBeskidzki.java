package pl.edu.pg.eti.ksg.po.lab2.symulatorwycieczki.ludzie;

public class PrzewodnikBeskidzki extends PrzewodnikStudencki {
    public PrzewodnikBeskidzki(String imie, String nazwisko, Plec plec) {
        super(imie, nazwisko, plec, 16);
    }

    @Override
    public int getUmiejetnosciNawigacyjne() {
        return 5;
    }
}
