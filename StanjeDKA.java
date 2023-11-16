import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StanjeDKA {

    public HashSet<LRStavka> lr_stavke_stanja = new HashSet<>();

    int brojStanja;

    public StanjeDKA(int brojStanja){
        this.brojStanja = brojStanja;
    }

    public Boolean jelStavkaTu(LRStavka stavka){
        for(LRStavka l : lr_stavke_stanja){
            if(l.equals(stavka)) return true;
        }
        return false;
    }

    @Override
    public String toString(){
        StringBuilder bob = new StringBuilder();
        bob.append("Stanje " + brojStanja + " {");
        for(LRStavka l : lr_stavke_stanja){
            bob.append(l.toString() + ", ");
        }
        bob.delete(bob.length() - 3, bob.length());
        bob.append("}");
        return bob.toString();
    }



}
