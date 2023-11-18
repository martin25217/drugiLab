import java.util.*;

public class Loader {

    public Set<String> nezavrsni_znakovi = new TreeSet<>();
    public Set<String> zavrsni_znakovi = new TreeSet<>();
    public Set<String> sinkronizacijski_znakovi = new TreeSet<>();

    public Set<Produkcija> produkcije = new HashSet<>();
    public String[][] zapIzrZn;
    public String[][] zapZn;
    public HashMap<String, HashSet<String>> zap;

    public String pocetniNezavsrniZnak;

    public Loader(){

        Scanner scanner = new Scanner(System.in);
        String[] string_nezavrsnih_znakova = scanner.nextLine().split(" ");
        this.pocetniNezavsrniZnak = string_nezavrsnih_znakova[1];

        for(int i = 1; i < string_nezavrsnih_znakova.length; i++){
            this.nezavrsni_znakovi.add(string_nezavrsnih_znakova[i]);
        }

        String[] string_zavrsnih_znakova = scanner.nextLine().split(" ");

        for(int i = 1; i < string_zavrsnih_znakova.length; i++){
            this.zavrsni_znakovi.add(string_zavrsnih_znakova[i]);
        }

        String[] string_sinkro_znakova = scanner.nextLine().split(" ");

        for(int i = 1; i < string_sinkro_znakova.length; i++){
            this.sinkronizacijski_znakovi.add(string_sinkro_znakova[i]);
        }

        String current_right_side;
        String current_left_side = null;
        int brojac = 0;
        while(scanner.hasNext()) {
            String string = scanner.nextLine();
            //System.out.println(string);


            if(string.charAt(0) != ' '){

                current_left_side = string;
            }else{

                current_right_side = string;

                this.produkcije.add(new Produkcija(current_left_side, current_right_side, brojac++));

            }
        }

        //zapocinjeIzravnoZnakom
        this.zapIzrZn = new String[nezavrsni_znakovi.size()][nezavrsni_znakovi.size() + zavrsni_znakovi.size()];
        for(int i = 0; i < nezavrsni_znakovi.size(); i++){
            for(int j = 0; j < nezavrsni_znakovi.size() + zavrsni_znakovi.size(); j++){
                zapIzrZn[i][j] = "NE";
            }
        }
        List<String> nezav = this.nezavrsni_znakovi.stream().toList();
        List<String> zav = this.zavrsni_znakovi.stream().toList();
        for(Produkcija p : this.produkcije){
            if(nezav.contains(p.desna_strana_produkcije.split(" ")[1])){
                zapIzrZn[nezav.indexOf(p.lijeva_strana_produkcije)][nezav.indexOf(p.desna_strana_produkcije.split(" ")[1])] = "DA";
            }else if(zav.contains(p.desna_strana_produkcije.split(" ")[1])){
                zapIzrZn[nezav.indexOf(p.lijeva_strana_produkcije)][nezav.size() + zav.indexOf(p.desna_strana_produkcije.split(" ")[1])] = "DA";
            }
        }
        //zapocinjeZnakom
        this.zapZn = new String[nezav.size() + zav.size()][nezav.size() + zav.size()];
        for(int i = 0; i < nezavrsni_znakovi.size() + zav.size(); i++){
            for(int j = 0; j < nezavrsni_znakovi.size() + zavrsni_znakovi.size(); j++){
               zapZn[i][j] = "NE";
            }
        }
        boolean promjena = true;
        while(promjena){
            promjena = false;
            for(int i = 0; i < nezav.size(); i++){
                for(int j = 0; j < nezav.size() + zav.size(); j++){
                    if(this.zapIzrZn[i][j].equals("DA")){
                        for(int k = 0; k < nezav.size() + zav.size(); k++){
                            if(j < nezav.size() && this.zapIzrZn[j][k].equals("DA") && this.zapZn[i][k].equals("NE")){
                                zapZn[i][k] = "DA";
                                promjena = true;
                            }
                        }
                    }
                }
            }
        }
        for(int i = 0; i < nezavrsni_znakovi.size(); i++){
            for(int j = 0; j < nezav.size() + zavrsni_znakovi.size(); j++){
                if(this.zapIzrZn[i][j].equals("DA")) this.zapZn[i][j] = "DA";
            }
        }
        //zapocinje
        this.zap = new HashMap<>();
        for(String n : nezav){
            HashSet<String> skup = new HashSet<>();
            for(int i = nezav.size(); i < nezav.size() + zav.size(); i++){
                if(this.zapZn[nezav.indexOf(n)][i].equals("DA")) skup.add(zav.get(i - nezav.size()));
            }
            this.zap.put(n, skup);
        }
        //zapocinjeZaProdukcije
        promjena = true;
        while(promjena){
            promjena = false;
            for(Produkcija p : this.produkcije){
                List<String> desno = Arrays.stream(p.desna_strana_produkcije.substring(1).split(" ")).toList();
                int i = 0;
                while(nezav.contains(desno.get(i))){
                    HashSet<String> helpMe = zap.get(desno.get(i));
                    for(String s : helpMe) {
                        HashSet<String> pom = zap.get(p.lijeva_strana_produkcije);
                        if(pom.add(s)) promjena = true;
                        zap.put(p.lijeva_strana_produkcije, pom);
                    }
                    i++;
                }
            }
        }

        for(Produkcija p : this.produkcije){
            p.zapocinje = zap.get(p.lijeva_strana_produkcije);
        }

    }

}
