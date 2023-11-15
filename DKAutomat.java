import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DKAutomat {

    StanjeDKA pocetno_stanje;

    HashSet<StanjeDKA> sva_stanja;

    HashSet<Tranzicije> funkcija_tranzicije;

    public DKAutomat(EpsilonAutomat e){

        List<LRStavka> lista =  e.stanja.keySet().stream().filter(x -> x.desno_od_tocke.split(" ")[0].split("")[0].equals("<")).collect(Collectors.toList());

        for(LRStavka stavka : lista){
            this.sva_stanja.add(new StanjeDKA(e,stavka, 0));
        }





    }
}
