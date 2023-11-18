package analizator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Analizator {
    public static void main(String[] args){
        Scanner skener = new Scanner(System.in);
        List<String[]> ulaz = new ArrayList<>();
        while(skener.hasNext()) ulaz.add(skener.nextLine().split(" "));
        LRParser parser = new LRParser();
    }
}
