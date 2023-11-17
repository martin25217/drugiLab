import java.util.*;
import java.util.stream.Collectors;

public class DKAutomat {

    StanjeDKA pocetno_stanje;

    HashSet<StanjeDKA> sva_stanja = new HashSet<>();

    HashSet<Tranzicije> funkcija_tranzicije;

    public DKAutomat(EpsilonAutomat e){
        int brojac = 0;
        //Dodajemo stanje koje je epsilon okolina onog dodatnog pocetnog stanja

        HashSet<Integer> epsilon_okolina_nule = new HashSet<>();
        epsilon_okolina_nule.add(0);
        Boolean pomocnik = true;


        while(pomocnik){
            pomocnik = false;
            HashSet<Integer> epsilon_okolina_nule_temp = (HashSet<Integer>) epsilon_okolina_nule.clone();
            for(Integer i : epsilon_okolina_nule){
                for(Tranzicije tr : e.funkcijaTranzicije){
                    if(i.equals(tr.pocetnoStanje) && tr.ucitanSimbol.equals("$")){
                        if(epsilon_okolina_nule_temp.add(tr.novoStanje)){
                            pomocnik = true;
                        }
                    }
                }
                epsilon_okolina_nule = epsilon_okolina_nule_temp;
            }
        }
        //System.out.println(epsilon_okolina_nule);

        StanjeDKA prvo_stanje = new StanjeDKA(brojac++, true);
        epsilon_okolina_nule.remove(0);
        epsilon_okolina_nule.stream().map(x-> pronadiKljuc(e.stanja, x)).forEach(x-> prvo_stanje.lr_stavke_stanja.add(x));
        this.sva_stanja.add(prvo_stanje);


        List<LRStavka> lista =  e.stanja.keySet().stream().filter(x -> x.desno_od_tocke.split(" ")[0].split("")[0].equals("<")).collect(Collectors.toList());


        //Grupiranje odredenih stavki radi jednostavnosi
        for(LRStavka l : lista){

            StanjeDKA stanje = new StanjeDKA(brojac++, false);
            //U novo stanje DKA dodajemo "izvor" epsilon okoline
            stanje.lr_stavke_stanja.add(l);
            Boolean nismo_gotovi = true;
            int brojac2 = 0;
            while(nismo_gotovi) {
                nismo_gotovi = false;
                StanjeDKA temp_stanje = stanje.clone();
                for (LRStavka lr : stanje.lr_stavke_stanja) {
                    for(Tranzicije tr : e.funkcijaTranzicije){
                        if(e.stanja.get(lr) == tr.pocetnoStanje && tr.ucitanSimbol.equals("$")){
                            if(temp_stanje.lr_stavke_stanja.add(tr.konacnaLRStavka)) nismo_gotovi = true;
                        }
                    }
                }
                stanje = temp_stanje;


            }

            sva_stanja.add(stanje);

        }

        //Sad moramo dodati one epsilon prijelaze koje još nismo

        for(LRStavka lr : e.stanja.keySet()){
            Boolean nemamo_momka_u_nekom_od_stanja = true;
            for(StanjeDKA s : this.sva_stanja){
                if(s.jelStavkaTu(lr)){
                    nemamo_momka_u_nekom_od_stanja = false;
                    break;
                }
            }
            if(nemamo_momka_u_nekom_od_stanja){
                StanjeDKA novo_stanje = new StanjeDKA(brojac++, false);
                novo_stanje.lr_stavke_stanja.add(lr);
                this.sva_stanja.add(novo_stanje);
            }
        }

        HashSet<StanjeDKA> sva_stanja_temp = (HashSet<StanjeDKA>) this.sva_stanja.clone();
        for(StanjeDKA stanje1 : this.sva_stanja){
            for(StanjeDKA stanje2 : this.sva_stanja){
                if(stanje1.isASubset(stanje2) && !stanje1.je_pocetno) sva_stanja_temp.remove(stanje1);
            }
        }
        this.sva_stanja = sva_stanja_temp;


        //Ode treba dodat konverziju tranzicjia nedeterminističkog konačnog automata s
        //epsilon prijelazima u tranzicjie determinističkog konačnog automata
        //U riječima premudrog Jagušta, VIKI DO YOUR KUNG FU
        this.funkcija_tranzicije = new HashSet<>();

        HashMap<Integer, LRStavka> stavkice = obrni(e);
        for(Tranzicije tr : e.funkcijaTranzicije){
            Set<Integer> listaPrvih = new HashSet<>();
            for(StanjeDKA s : sva_stanja){
                if(s.lr_stavke_stanja.contains(stavkice.get(tr.pocetnoStanje))) listaPrvih.add(s.brojStanja);
            }
            Set<Integer> listaDrugih = new HashSet<>();
            for(StanjeDKA s : sva_stanja){
                if(s.lr_stavke_stanja.contains(stavkice.get(tr.novoStanje))) listaDrugih.add(s.brojStanja);
            }
            for(int p : listaPrvih){
                for(int d : listaDrugih){
                    if(!tr.ucitanSimbol.equals("$"))funkcija_tranzicije.add(new Tranzicije(p, tr.ucitanSimbol, d, stavkice.get(p), stavkice.get(d)));
                }
            }
        }

    }

    public HashMap<Integer, LRStavka> obrni(EpsilonAutomat e){
        HashMap<Integer, LRStavka> res = new HashMap<>();
        for(LRStavka l : e.stanja.keySet()) res.put(e.stanja.get(l), l);
        return  res;
    }

    public LRStavka pronadiKljuc(HashMap<LRStavka,Integer> mapa, int i){
        for(LRStavka lr : mapa.keySet()){
            if(mapa.get(lr).equals(i)) return lr;
        }
        return null;

    }

}
