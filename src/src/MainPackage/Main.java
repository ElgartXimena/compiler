package MainPackage;

import AnalizadorLexico.Analizador_Lexico;
import AnalizadorSintactico.Parser;
import Pruebas.CargarPruebas;
import Pruebas.EjecutorPruebas;

import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        /*Analizador_Lexico la = new Analizador_Lexico();
        ArrayList<Integer> list = new ArrayList<>();
        int tk = -1;
        while (tk != 0) {
            tk = la.yylex();
            list.add(tk);
            System.out.println("Token: " + tk + " Lexema: " + la.getBuffer());
            System.out.println(" ");
        }
        System.out.println("Lista: " + list);*/
       // EjecutorPruebas.run(CargarPruebas.cargar());
        //Parser p = new Parser();
        //p.run();
        String cod = "{\n" +
                "a = 129_s,\n" +
                "b -= 123_s,\n" +
                "a = 34_s + 55_s,\n" +
                "c = 1_s,\n" +
                "}$";
        Analizador_Lexico al = new Analizador_Lexico(cod.toCharArray());
        int tk = -1;
        while (tk != 0) {
            //System.out.println(al.getTablaSimbolos().toString());
            System.out.println(" ");
            tk = al.yylex();
        }
        System.exit(0);
    }
}