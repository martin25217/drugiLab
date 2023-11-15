import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class Loader {

    public Set<String> nezavrsni_znakovi = new TreeSet<>();
    public Set<String> zavrsni_znakovi = new TreeSet<>();
    public Set<String> sinkronizacijski_znakovi = new TreeSet<>();

    public Set<Produkcija> produkcije = new HashSet<>();

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




    }
}
