package generator;

public class Tranzicije {

    int pocetnoStanje;
    String ucitanSimbol;
    int novoStanje;

    LRStavka pocetnaLRStavka;
    LRStavka konacnaLRStavka;

    public Tranzicije(int pocetnoStanje, String ucitanSimbol, int novoStanje, LRStavka pocetnaLRStavka, LRStavka konacnaLRStavka) {
        this.pocetnoStanje = pocetnoStanje;
        this.ucitanSimbol = ucitanSimbol;
        this.novoStanje = novoStanje;
        this.pocetnaLRStavka = pocetnaLRStavka;
        this.konacnaLRStavka = konacnaLRStavka;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        Tranzicije that = (Tranzicije) object;

        if (pocetnoStanje != that.pocetnoStanje) return false;
        if (novoStanje != that.novoStanje) return false;
        return ucitanSimbol.equals(that.ucitanSimbol);
    }

    @Override
    public int hashCode() {
        int result = pocetnoStanje;
        result = 31 * result + ucitanSimbol.hashCode();
        result = 31 * result + novoStanje;
        return result;
    }

    @Override
    public String toString(){

        return ("(" + this.pocetnoStanje + "," + this.ucitanSimbol + ") -> " + this.novoStanje);

    }


}
