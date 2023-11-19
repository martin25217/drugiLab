package analizator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.*;

import generator.TablicaLRParsera;

public class Analizator {
    public static void main(String[] args){
        Scanner skener = new Scanner(System.in);
        ArrayList<String[]> ulaz = new ArrayList<>();
        while(skener.hasNext()) ulaz.add(skener.nextLine().split(" "));
        String[] kraj = {"$"};
        ulaz.add(kraj);
        LRParser parser = new LRParser();



        try
        {

            // Reading the object from a file
            FileInputStream file = new FileInputStream("analizator/tablica.txt");

            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            TablicaLRParsera object1 = (TablicaLRParsera) in.readObject();

            in.close();
            file.close();

            //System.out.println("Object has been deserialized ");
            //object1.printajTablicuA();

            //object1.printajTablicuNS();


            parser.nezavrsnaStanja = new HashMap<>();
            parser.zavrsnaStanja = new HashMap<>();
            parser.tablicaNovoStanje = new String[object1.nezavrsniZnakovi.size()][object1.max];
            parser.tablicaAkcije = new String[object1.zavrsniZnakovi.size()][object1.max];
            for(String s : object1.nezavrsniZnakovi.keySet()){
                parser.nezavrsnaStanja.put(s, object1.nezavrsniZnakovi.get(s));
            }
            for(String s : object1.zavrsniZnakovi.keySet()){
                parser.zavrsnaStanja.put(s, object1.zavrsniZnakovi.get(s));
            }
            for(int i = 0; i < object1.nezavrsniZnakovi.size(); i++){
                for(int j = 0; j < object1.max; j++){
                    parser.tablicaNovoStanje[i][j] = object1.tablicaNovogStanja[i][j];
                }
            }
            for(int i = 0; i < object1.zavrsniZnakovi.size(); i++){
                for(int j = 0; j < object1.max; j++){
                    parser.tablicaAkcije[i][j] = object1.tablicaAkcija[i][j];
                }
            }
            parser.ListaProdukcija = object1.listaLR;
            parser.sinkronizacijski = object1.sink;

        }

        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }

        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }

        parser.parse(ulaz);

    }
}
