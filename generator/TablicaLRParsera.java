package generator;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TablicaLRParsera  implements Serializable {

    public String[][] tablicaAkcija;
    public String[][] tablicaNovogStanja;

    public HashMap<String, Integer> zavrsniZnakovi;
    public HashMap<String, Integer> nezavrsniZnakovi;

    public String pocetni_nezavrsni_znak;

    public int max;

    public HashSet<Produkcija> listaLR;
    public String[] sink;

    public TablicaLRParsera(DKAutomat a, Loader loader){
        this.pocetni_nezavrsni_znak = loader.pocetniNezavsrniZnak;

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
        this.tablicaAkcija = new String[zavrsniZnakovi.size()][max];

        for(int i = 0; i < nezavrsniZnakovi.size(); i++){
            for(int j = 0; j < max; j++){
                this.tablicaNovogStanja[i][j] = "";
            }
        }
        for(int i = 0; i < zavrsniZnakovi.size(); i++){
            for(int j = 0; j < max; j++){
                this.tablicaAkcija[i][j] = "";
            }
        }

        /*for(generator.Tranzicije t : a.funkcija_tranzicije){
            System.out.println(t.toString());
        }*/

        for(int i = 0; i < nezavrsniZnakovi.size(); i++){
            for(int j = 0; j < max; j++) this.tablicaNovogStanja[i][j] = "";
        }
        for(int i = 0; i < zavrsniZnakovi.size(); i++){
            for(int j = 0; j < max; j++) this.tablicaAkcija[i][j] = "";
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
           for(LRStavka lr : stanje.lr_stavke_stanja){
               if(lr.potpuna){
                   for(String str : this.zavrsniZnakovi.keySet()){//Ako bude sranja tu treba gledat
                       if(this.tablicaAkcija[zavrsniZnakovi.get(str)][stanje.brojStanja].equals("")){
                           this.tablicaAkcija[zavrsniZnakovi.get(str)][stanje.brojStanja] = "R" + lr.redniBrojStavke;
                       }else{
                           if((!this.tablicaAkcija[zavrsniZnakovi.get(str)][stanje.brojStanja].equals("")) && lr.redniBrojStavke < Integer.parseInt((this.tablicaAkcija[zavrsniZnakovi.get(str)][stanje.brojStanja].substring(1)))){
                               this.tablicaAkcija[zavrsniZnakovi.get(str)][stanje.brojStanja] = "R" + lr.redniBrojStavke;
                           }
                       }

                   }
               }
           }
        }

        //Sad idu stanja u tablica akcija
        for(Tranzicije t : a.funkcija_tranzicije){
            for(StanjeDKA s: a.sva_stanja ){
                if(t.pocetnoStanje == s.brojStanja){
                    //System.out.println("Za stanje " + s.toString() + " i tranziciju " + t.toString());
                    Set<LRStavka> set = s.lr_stavke_stanja.stream().filter(x-> (this.zavrsniZnakovi.containsKey(x.desno_od_tocke.split(" ")[0]) &&  x.desno_od_tocke.split(" ")[0].equals(t.ucitanSimbol))).collect(Collectors.toSet());
                    //ako set.size nije 1 onda imamo nejednoznacnost koju treba razrijesiti
                    for(LRStavka stavka : set){
                        this.tablicaAkcija[this.zavrsniZnakovi.get(t.ucitanSimbol)][t.pocetnoStanje] = "P" + t.novoStanje;
                    }
                }
            }
        }

        for(StanjeDKA stanje : a.sva_stanja){
            for(LRStavka lr : stanje.lr_stavke_stanja){
                System.out.println(lr.toString() + " " + lr.potpuna + " " + lr.lijeva_strana_produkcije.equals(this.pocetni_nezavrsni_znak) + " " + this.pocetni_nezavrsni_znak);
                if(lr.potpuna && lr.lijeva_strana_produkcije.equals(this.pocetni_nezavrsni_znak) && lr.zapocinje.contains("$")){
                    System.out.println("Usli smo");
                    this.tablicaAkcija[this.zavrsniZnakovi.get("$")][stanje.brojStanja] = "Okay";
                }
            }
        }

        this.listaLR = (HashSet<Produkcija>) loader.produkcije;
        this.sink = new String[loader.sinkronizacijski_znakovi.size()];
        int i = 0;
        for(String s : loader.sinkronizacijski_znakovi){
            this.sink[i] = s;
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

    public void printajTablicuA(){
        for(int i = 0; i < zavrsniZnakovi.size(); i++){
            StringBuilder izlaz = new StringBuilder();
            for(int j = 0; j < max; j++){
                if(tablicaAkcija[i][j].isEmpty()) izlaz.append("--|");
                else izlaz.append(tablicaAkcija[i][j] + "|");
            }
            System.out.println(izlaz);
        }
    }
}
