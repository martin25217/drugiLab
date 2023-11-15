import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StanjeDKA {

    public HashSet<LRStavka> lr_stavke_stanja = new HashSet<>();

    int brojStanja;

    public StanjeDKA(EpsilonAutomat e, LRStavka l, int brojStanja) {
        this.brojStanja = brojStanja;



    }



}
