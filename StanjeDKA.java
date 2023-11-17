import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StanjeDKA {

    public HashSet<LRStavka> lr_stavke_stanja = new HashSet<>();

    int brojStanja;

    public Boolean je_pocetno;

    public StanjeDKA(int brojStanja, Boolean je_pocetno){

        this.brojStanja = brojStanja;
        this.je_pocetno = je_pocetno;
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

    @Override
    public StanjeDKA clone(){
        StanjeDKA result = new StanjeDKA(this.brojStanja, this.je_pocetno);
        result.lr_stavke_stanja = (HashSet<LRStavka>) this.lr_stavke_stanja.clone();
        return result;
    }

    public Boolean isASubset(StanjeDKA stanje){
        Boolean resultat = true;
        for(LRStavka l : this.lr_stavke_stanja){
            if(!stanje.lr_stavke_stanja.contains(l)){
                resultat = false;
                break;
            }
        }
        return resultat && (this.lr_stavke_stanja.size() != stanje.lr_stavke_stanja.size());
    }



}
