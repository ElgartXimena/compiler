package AnalizadorLexico;

import java.util.HashMap;

public class Tabla_Simbolos {
    private HashMap<String, Token> tabla;
    public Tabla_Simbolos() {
        tabla = new HashMap();
    }
    public void insertarSimbolo(Token tk){
        tabla.put(tk.getLexema(),tk);
    }
    public Token getToken(String bf){
        return tabla.get(bf);
    }

    public boolean existeSimbolo(String bf){
        return tabla.containsKey(bf);
    }
}
