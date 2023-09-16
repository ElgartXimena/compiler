package Pruebas;

import AnalizadorLexico.Analizador_Lexico;

import java.util.ArrayList;
import java.util.Objects;

public class Prueba {
    private char[] codigo;
    private String id;
    private ArrayList<Integer> resultado;

    public Prueba(ArrayList<Integer> result, String idprueba) {
        this.resultado = result;
        this.id = idprueba;
    }

    public void setCodigo(char[] codigo) {
        this.codigo = codigo;
    }

    public void run(){
        Analizador_Lexico la = new Analizador_Lexico(codigo);
        ArrayList<Integer> list = new ArrayList<>();
        int tk = -1;
        int index = 0;
        System.out.println("Corriendo prueba "+id);
        String result = "Prueba " + id + " superada";
        while (tk != 0) {
            tk = la.yylex();
            list.add(tk);
            if (!Objects.equals(resultado.get(index), list.get(index))){
                System.out.println("Error en prueba "+id+" es esperaba token "+resultado.get(index)+" pero se obtuvo "+list.get(index));
                result = "Prueba " + id + " fallada";
            }
            index++;
        }
        System.out.println(result);
    }
}
