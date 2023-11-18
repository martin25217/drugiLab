import java.util.HashSet;

public class Produkcija {

    public String lijeva_strana_produkcije;
    public String desna_strana_produkcije;

    public int duljina_desne_strane;
    public int redniBrojProdukcije;

    public HashSet<String> zapocinje;

    public Produkcija(String lijeva_strana_produkcije, String desna_strana_produkcije, int redniBrojProdukcije){
        this.lijeva_strana_produkcije = lijeva_strana_produkcije;
        this.desna_strana_produkcije = desna_strana_produkcije;
        this.duljina_desne_strane = desna_strana_produkcije.split(" ").length;
        this.redniBrojProdukcije = redniBrojProdukcije;
        this.zapocinje = new HashSet<>();

    }

    @Override
    public String toString(){
        return lijeva_strana_produkcije + " -> " + desna_strana_produkcije;
    }


}
