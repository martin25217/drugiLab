package generator;

import java.util.*;

import java.util.stream.Collectors;

public class EpsilonAutomat {

    public HashMap<LRStavka, Integer> stanja = new HashMap<>();
    public HashSet<Tranzicije> funkcijaTranzicije = new HashSet<>();

    public Set<Integer> pocetnaStanja = new HashSet<>();



    public EpsilonAutomat(Set<LRStavka> set, String pocetniNezavrsniZnak){
        int brojac = 1;

        for(LRStavka l : set){
            stanja.put(l, brojac++);
        }
        List<LRStavka> pocetneLRStavke = set.stream().filter(x -> x.lijevo_od_tocke.isEmpty() && !x.desno_od_tocke.isEmpty()).collect(Collectors.toList());

        //generacija svih lanaca
        for(LRStavka l : pocetneLRStavke){
            generateChain(set, l);
        }

        //Dodavanje epsilon prijelaza
        for(LRStavka l : set){
            //Gledamo treba li dodati epsilon prijelaz za analizu
            if(l.desno_od_tocke.split(" ")[0].split("")[0].equals("<")){
                for(LRStavka pocetakLanca : set){
                    if(pocetakLanca.lijeva_strana_produkcije.equals(l.desno_od_tocke.split(" ")[0]) && pocetakLanca.lijevo_od_tocke.equals("")){
                        this.funkcijaTranzicije.add(new Tranzicije(this.stanja.get(l), "$", this.stanja.get(pocetakLanca), l, pocetakLanca));
                    }
                }
            }
        }
        //Dodavanje dodatnog pocetnog stanja
        this.pocetnaStanja.add(0);

        //Spajamo dodatno pocetno stanje na ostatak automata
        for(LRStavka l : set){
            if(l.lijeva_strana_produkcije.equals(pocetniNezavrsniZnak) && (l.lijevo_od_tocke.equals("") /*&& !l.desno_od_tocke.equals("")*/)){
                this.funkcijaTranzicije.add(new Tranzicije(0, "$", this.stanja.get(l), null, l));
            }
        }



    }

    public void generateChain(Set<LRStavka> set, LRStavka pocetak){
        for(LRStavka l : set){
            if(l.lijeva_strana_produkcije.equals(pocetak.lijeva_strana_produkcije)){
                Boolean jednakostDjelovaLRStavki = pocetak.desno_od_tocke.split(" ")[0].equals(l.lijevo_od_tocke.split(" ")[l.lijevo_od_tocke.split(" ").length - 1]);
                Boolean nemamoKrug = !(pocetak.lijevo_od_tocke.equals("") && l.desno_od_tocke.equals(""));
                Boolean nemamoDrugiKrug = !(pocetak.desno_od_tocke.equals("") && l.lijevo_od_tocke.equals(""));
                Boolean podudarajuLiSe = (pocetak.lijevo_od_tocke + pocetak.desno_od_tocke).equals(l.lijevo_od_tocke + l.desno_od_tocke);
                Boolean jedanZnak = pocetak.desno_od_tocke.equals(l.lijevo_od_tocke) && pocetak.desno_od_tocke.length() == 2 && l.lijevo_od_tocke.length() == 2;
                if(jednakostDjelovaLRStavki && nemamoKrug && nemamoDrugiKrug && podudarajuLiSe || jedanZnak){

                    generateChain(set, l);
                    this.funkcijaTranzicije.add(new Tranzicije(stanja.get(pocetak), pocetak.desno_od_tocke.split(" ")[0], stanja.get(l), pocetak, l));
                }
            }
        }

    }


}
