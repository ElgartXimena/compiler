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
        //EjecutorPruebas.run(CargarPruebas.cargar());
        Parser p = new Parser();
        p.run();
        System.exit(0);
    }
}