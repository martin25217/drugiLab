import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Loader loader = new Loader();
        Set<LRStavka> stavke = loader.produkcije.stream().map(x -> LRStavka.generirajLRStavke(x)).flatMap(Set::stream).collect(Collectors.toSet());
        System.out.println(loader.pocetniNezavsrniZnak);


        EpsilonAutomat e = new EpsilonAutomat(stavke, loader.pocetniNezavsrniZnak);

        /*System.out.println("Tablica enkodiranja");
        for(LRStavka l : e.stanja.keySet()){
            System.out.println(l.toString() + " | " + e.stanja.get(l));
        }

        System.out.println("Evo tranzicija");
        for(Tranzicije t : e.funkcijaTranzicije){
            System.out.println(t.toString());
        }*/

        DKAutomat a = new DKAutomat(e);
    }
}