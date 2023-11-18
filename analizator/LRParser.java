package analizator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;
//import "../StanjeDKA.java";

public class LRParser{
    ArrayList<String> nezavrsnaStanja;
    ArrayList<String> zavrsnaStanja;
    String[][] tablicaAkcije;
    String[][] tablicaNovoStanje;
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
            String akcija = tablicaAkcije[Integer.parseInt(currState)][nezavrsnaStanja.indexOf(currSimbol)];
            if(akcija.equals("Pomakni")){
                stog.push(currSimbol);
                stog.push(akcija.substring(1));
                inputIndex++;
            }else if(akcija.equals("Reduciraj")){
                Integer pravilo = Integer.parseInt(akcija.substring(1));
                //sto je pravilo to je iduci korak -> gledaj biljeznicu
            }else if(akcija.equals("Prihvaca")){
                System.out.println("prihvaceno");
                break;
            }else{
                System.out.println("greska");
                break;
            }
        }
    }
}