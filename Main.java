import generator.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {

        Loader loader = new Loader();

        //System.out.println(loader.zap);

        Set<LRStavka> stavke = loader.produkcije.stream().map(x -> LRStavka.generirajLRStavke(x)).flatMap(Set::stream).collect(Collectors.toSet());

        EpsilonAutomat e = new EpsilonAutomat(stavke, loader.pocetniNezavsrniZnak);

        //System.out.println("Tablica enkodiranja");
        /*for(generator.LRStavka l : e.stanja.keySet()){
            System.out.println(l.toString() + " | " + e.stanja.get(l));
        }*/

        DKAutomat a = new DKAutomat(e);
        /*for(generator.StanjeDKA s : a.sva_stanja){
            System.out.println(s.toString());
        }/*
        System.out.println(a.sva_stanja.size() + " " + a.funkcija_tranzicije.size());
        for(generator.Tranzicije s : a.funkcija_tranzicije){
            System.out.println(s.toString());
        }*/

        TablicaLRParsera tablica = new TablicaLRParsera(a, loader);
        tablica.printajTablicuA();
        //tablica.printajTablicuNS();

        try (final FileOutputStream fout = new FileOutputStream("analizator/tablica.txt");
             final ObjectOutputStream out = new ObjectOutputStream(fout)) {
            out.writeObject(tablica);
            out.flush();
            System.out.println("success");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
