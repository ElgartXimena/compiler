package MainPackage;

import AnalizadorLexico.Analizador_Lexico;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Analizador_Lexico la = new Analizador_Lexico();
        ArrayList<Integer> list = new ArrayList<>();
        int tk = -1;
        while (tk != 0) {
            tk = la.yylex();
            list.add(tk);
            System.out.println("Token: " + tk + " Lexema: " + la.getBuffer());
        }
        System.out.println("Lista: " + list);
        System.exit(0);
    }
}