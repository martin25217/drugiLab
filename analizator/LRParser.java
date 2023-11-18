package analizator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
//import "../StanjeDKA.java";

public class LRParser{
    ArrayList<String> nezavrsnaStanja;
    ArrayList<String> zavrsnaStanja;
    String[][] tablicaAkcije;
    String[][] tablicaNovoStanje;
    String[] lrStavke;
    TreeNode generativnoStablo;

    public LRParser(){
        this.generativnoStablo = new TreeNode();
    }
    public void parse(ArrayList<String[]> input){
        Stack<String> stog = new Stack<>();
        stog.push("0");
        int inputIndex = 0;
        while(true){
            String currState = stog.peek();
            String currSimbol = input.get(inputIndex)[0];
            String akcija = tablicaAkcije[Integer.parseInt(currState)][nezavrsnaStanja.indexOf(currSimbol)];//jesu li ovdje nezavrsna stanja -> ERROR
            if(akcija.equals("Pomakni")){
                stog.push(currSimbol);
                stog.push(akcija.substring(1));
                inputIndex++;
            }else if(akcija.equals("Reduciraj")){//ovdje treba provjeriti substringove kad napravis ucitavanje
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
                String novoStanje = tablicaNovoStanje[Integer.parseInt(stanje)][zavrsnaStanja.indexOf(roditelj.getData())];
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