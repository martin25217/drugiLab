package analizator;

import generator.LRStavka;
import generator.Produkcija;

import java.util.*;
//import "../generator.StanjeDKA.java";

public class LRParser{
    public HashMap<String, Integer> nezavrsnaStanja;
    public HashMap<String, Integer> zavrsnaStanja;
    public String[][] tablicaAkcije;
    public String[][] tablicaNovoStanje;
    public HashSet<Produkcija> ListaProdukcija;
    public String[] sinkronizacijski;
    public TreeNode generativnoStablo;



    public LRParser(){
        this.generativnoStablo = new TreeNode();
    }
    public void parse(ArrayList<String[]> input){
        int br = 0;
        for(Produkcija l : ListaProdukcija){
            if(l.redniBrojProdukcije > br) br = l.redniBrojProdukcije;
        }

        String[] lrStavke = new String[br + 1];
        for(Produkcija l : ListaProdukcija){
            lrStavke[l.redniBrojProdukcije] = l.lijeva_strana_produkcije + " -> " + l.desna_strana_produkcije;
        }


        Stack<String> stog = new Stack<>();
        stog.push("0");
        int inputIndex = 0;
        while(true){
            String currState = stog.peek();
            String currSimbol = input.get(inputIndex)[0];
            String akcija = tablicaAkcije[zavrsnaStanja.get(currSimbol)][Integer.parseInt(currState)];
            System.out.println(akcija);
            if(akcija.startsWith("P")){
                stog.push(currSimbol);
                stog.push(akcija.substring(1));
                inputIndex++;
                String lookahead = input.get(inputIndex)[0];
                for(Produkcija p : ListaProdukcija){
                    if(p.zapocinje.contains(lookahead)) break;
                    else if(p.zapocinje.contains("$"))
                }
            }else if(akcija.startsWith("R")){//ovdje treba provjeriti substringove kad napravis ucitavanje
                Integer pravilo = Integer.parseInt(akcija.substring(1));
                Stack<String> pomocni = new Stack<>();
                for(int i = 0; i < lrStavke[pravilo].split(" ").length - 2; i++){
                    stog.pop();
                    pomocni.push(stog.pop());
                }
                TreeNode roditelj = new TreeNode(lrStavke[pravilo].split(" ")[0]);
                for(int i = 0; i < pomocni.size(); i++){
                    if(generativnoStablo.getData().equals(pomocni.peek())){
                        roditelj.addChild(this.generativnoStablo);
                    }else{
                        roditelj.addChild(new TreeNode(pomocni.pop()));
                    }
                }
                String stanje = stog.peek();
                String novoStanje = tablicaNovoStanje[Integer.parseInt(stanje)][zavrsnaStanja.get(roditelj.getData())];
                stog.push(roditelj.getData());
                stog.push(novoStanje.substring(1));
                generativnoStablo = roditelj;
            }else if(akcija.equals("Prihvaca")){
                TreeNode roditelj = new TreeNode(stog.peek());
                roditelj.addChild(generativnoStablo);
                generativnoStablo = roditelj;
                break;
            }else{
                //STO SAD TU
                System.out.println("greska");
                break;
            }
        }
    }
}