import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Loader loader = new Loader();
        Set<LRStavka> stavke = loader.produkcije.stream().map(x -> LRStavka.generirajLRStavke(x)).flatMap(Set::stream).collect(Collectors.toSet());
        //System.out.println(loader.pocetniNezavsrniZnak);

        EpsilonAutomat e = new EpsilonAutomat(stavke, loader.pocetniNezavsrniZnak);

        /*System.out.println("Tablica enkodiranja");
        for(LRStavka l : e.stanja.keySet()){
            System.out.println(l.toString() + " | " + e.stanja.get(l));
        }*/

        DKAutomat a = new DKAutomat(e);
        /*for(StanjeDKA s : a.sva_stanja){
            System.out.println(s.toString());
        }
        System.out.println(a.sva_stanja.size() + " " + a.funkcija_tranzicije.size());
        for(Tranzicije s : a.funkcija_tranzicije){
            System.out.println(s.toString());
        }*/

        TablicaLRParsera tablica = new TablicaLRParsera(a, loader);

        tablica.printajTablicuNS();
    }
}
