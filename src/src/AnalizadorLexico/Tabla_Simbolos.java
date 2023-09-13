package AnalizadorLexico;

import java.util.HashMap;

public class Tabla_Simbolos {
    private HashMap<String, AtributosLexema> tabla;
    public Tabla_Simbolos() {
        tabla = new HashMap();
    }
    public void insertarSimbolo(String lexema, AtributosLexema tk){
        tabla.put(lexema,tk);
    }
    public AtributosLexema getAtributos(String lexema){
        return tabla.get(lexema);
    }

    public boolean existeSimbolo(String bf){
        return tabla.containsKey(bf);
    }
}
