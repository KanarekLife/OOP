package pl.edu.pg.eti.ksg.po.lab2.symulatorwycieczki;

public abstract class Atrakcja implements ElementWycieczki {
    private double czas;

    protected Atrakcja(double czas) {
        this.czas = czas;
    }

    public double getCzas() {
        return czas;
    }
}
