import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

public class TablicaLRParsera {

    String[][] tablicaAkcija;
    String[][] tablicaNovogStanja;

    HashMap<String, Integer> zavrsniZnakovi;
    HashMap<String, Integer> nezavrsniZnakovi;

    int max;


    public TablicaLRParsera(DKAutomat a, Loader loader){
        int brojac = 0;
        //zavrsni znakovi
        this.zavrsniZnakovi = new HashMap<>();
        for(String s : loader.zavrsni_znakovi) this.zavrsniZnakovi.put(s, brojac++);

        brojac = 0;
        //nezavrsni znakovi
        this.nezavrsniZnakovi = new HashMap<>();
        for(String s : loader.nezavrsni_znakovi) this.nezavrsniZnakovi.put(s, brojac++);

        //trazenje najveceg broja u nazivu stanja DKA kad maknemo one viskove
        //jer ni manje ni vise pojavljuju se stanja koja imaju veci broj stanja nego koliko je stanja
        max = 0;
        for(StanjeDKA s : a.sva_stanja){
            if(s.brojStanja > max) max = s.brojStanja;
        }
        max += 1;

        //incijalizacja tablica
        this.tablicaNovogStanja = new String[nezavrsniZnakovi.size()][max];
        this.tablicaAkcija = new String[zavrsniZnakovi.size() + 1][max];

        for(int i = 0; i < nezavrsniZnakovi.size(); i++){
            for(int j = 0; j < max; j++){
                this.tablicaNovogStanja[i][j] = "";
            }
        }
        for(int i = 0; i < zavrsniZnakovi.size() + 1; i++){
            for(int j = 0; j < max; j++){
                this.tablicaAkcija[i][j] = "";
            }
        }

        for(Tranzicije t : a.funkcija_tranzicije){
            System.out.println(t.toString());
        }

        //tablica novog stanja
        for(Tranzicije t : a.funkcija_tranzicije){
            //tablica novog stanja
            if(t.ucitanSimbol.startsWith("<")){
                tablicaNovogStanja[nezavrsniZnakovi.get(t.ucitanSimbol)][t.pocetnoStanje] = "S" + t.novoStanje;
            }
        }

        //Dodavanje redukcija u tablicu akcija
        for(StanjeDKA stanje : a.sva_stanja){
            stanje.
        }

        //Sad ide tablica akcija
        for(Tranzicije t : a.funkcija_tranzicije){
            for(StanjeDKA s: a.sva_stanja ){
                if(t.pocetnoStanje == s.brojStanja){
                    System.out.println("Za stanje " + s.toString() + " i tranziciju " + t.toString());
                    Set<LRStavka> set = s.lr_stavke_stanja.stream().filter(x-> (this.zavrsniZnakovi.containsKey(x.desno_od_tocke.split(" ")[0]) &&  x.desno_od_tocke.split(" ")[0].equals(t.ucitanSimbol))).collect(Collectors.toSet());
                    //ako set.size nije 1 onda imamo nejednoznacnost koju treba razrijesiti
                    for(LRStavka stavka : set){
                        System.out.println(stavka.toString());
                    }
                }
            }
        }
    }

    public void printajTablicuNS(){
        for(int i = 0; i < nezavrsniZnakovi.size(); i++){
            StringBuilder izlaz = new StringBuilder();
            for(int j = 0; j < max; j++){
                if(tablicaNovogStanja[i][j].isEmpty()) izlaz.append("--|");
                else izlaz.append(tablicaNovogStanja[i][j] + "|");
            }
            System.out.println(izlaz);
        }
    }
}
