import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class DKAutomat {

    StanjeDKA pocetno_stanje;

    HashSet<StanjeDKA> sva_stanja = new HashSet<>();

    HashSet<Tranzicije> funkcija_tranzicije;

    public DKAutomat(EpsilonAutomat e){

        List<LRStavka> lista =  e.stanja.keySet().stream().filter(x -> x.desno_od_tocke.split(" ")[0].split("")[0].equals("<")).collect(Collectors.toList());
        int brojac = 0;

        //Grupiranje odredenih stavki radi jednostavnosi
        for(LRStavka l : lista){
            System.out.println("Alo bado eto san iz pocetak__");
            StanjeDKA stanje = new StanjeDKA(brojac++);
            stanje.lr_stavke_stanja.add(l);
            Boolean bool = true;

            while(bool){
                bool = false;
                for(Tranzicije tr : e.funkcijaTranzicije){
                    if(tr.pocetnoStanje == e.stanja.get(l) && tr.ucitanSimbol.equals("$")){
                        if(stanje.lr_stavke_stanja.add(tr.konacnaLRStavka)){
                            bool = true;
                            System.out.println("Za LRstavku " + tr.pocetnaLRStavka.toString() + " sam dodao u stanje LR stavku: " + tr.konacnaLRStavka.toString());
                        }


                    }

                }
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
                StanjeDKA novo_stanje = new StanjeDKA(brojac++);
                novo_stanje.lr_stavke_stanja.add(lr);
                this.sva_stanja.add(novo_stanje);
            }
        }

    }

}
