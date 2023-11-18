import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.TreeSet;

public class LRStavka{

    public String lijeva_strana_produkcije;
    public String lijevo_od_tocke;
    public String desno_od_tocke;
    public HashSet<String> zapocinje;

    public Boolean potpuna = false;


    int redniBrojStavke;


    public LRStavka(Produkcija p, int x, int redniBrojStavke){
        this.redniBrojStavke = redniBrojStavke;
        this.lijeva_strana_produkcije = p.lijeva_strana_produkcije;

        this.zapocinje = new HashSet<>();

        if(p.desna_strana_produkcije.equals(" $")){

            this.desno_od_tocke ="";
            this.lijevo_od_tocke ="";
            this.zapocinje = p.zapocinje;
            return;
        }

        String[] helper = p.desna_strana_produkcije.split(" ");

        int counter = 0;
        for(int i = 0; i < helper.length; i++){
            if(!helper[i].isEmpty()) counter++;
        }

        String[] correctHelper = new String[counter];

        int brojac = 0;
        for(int i = 0; i < helper.length; i++){
            if(!helper[i].isEmpty()){
                correctHelper[brojac++] = helper[i];
            }
        }

        StringBuilder bob = new StringBuilder();
        for(int i = 0; i < x; i++){
            bob.append(correctHelper[i] + " ");
        }
        this.lijevo_od_tocke = bob.toString();
        bob.setLength(0);

        for(int i = x; i < correctHelper.length; i++){
            bob.append(correctHelper[i] + " ");
        }
        this.desno_od_tocke = bob.toString();

        if(this.desno_od_tocke.isEmpty()) this.potpuna = true;

        this.zapocinje = p.zapocinje;

    }

    public static HashSet<LRStavka> generirajLRStavke(Produkcija p){
        HashSet<LRStavka> resultat = new HashSet<>();
        //Popravak

        for(int i = 0; i < p.duljina_desne_strane; i++){
            LRStavka lr = new LRStavka(p,i, p.redniBrojProdukcije);
            if(lr.lijevo_od_tocke.equals("")) lr.potpuna = true;
            else lr.potpuna = false;
            resultat.add(lr);
        }

        return resultat;
    }

    @Override
    public String toString(){
        return lijeva_strana_produkcije + " -> " + lijevo_od_tocke + " * " + desno_od_tocke + " | " + zapocinje;
    }

    @Override
    public boolean equals(Object o){
        LRStavka drugi = (LRStavka) o;
        return (this.redniBrojStavke == drugi.redniBrojStavke &&
        this.lijeva_strana_produkcije.equals(drugi.lijeva_strana_produkcije) &&
        this.lijevo_od_tocke.equals(drugi.lijevo_od_tocke) &&
        this.desno_od_tocke.equals(drugi.desno_od_tocke));
    }

    @Override
    public int hashCode() {
        return Objects.hash(lijeva_strana_produkcije, lijevo_od_tocke, desno_od_tocke, redniBrojStavke);
    }
}
