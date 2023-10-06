package AnalizadorLexico;

import java.util.HashMap;

public class Tabla_Simbolos {
    private HashMap<String, AtributosLexema> tabla;
    public Tabla_Simbolos() {
        tabla = new HashMap();
    }
    public void insertarSimbolo(String lexema, AtributosLexema at){
        tabla.put(lexema,at);
    }
    public AtributosLexema getAtributos(String lexema){
        return tabla.get(lexema);
    }
    public void modificarClave(String lexemaOld, String lexemaNew){
        AtributosLexema at = tabla.remove(lexemaOld);
        tabla.put(lexemaNew,at);
    }
    public boolean existeSimbolo(String bf){
        return tabla.containsKey(bf);
    }
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("| Lexema    | Tipo           | Uso             | CantUsos |\n");
        sb.append("|-----------|----------------|-----------------|----------|\n");

        for (String lexema : tabla.keySet()) {
            AtributosLexema atributos = tabla.get(lexema);
            sb.append("| " + lexema + "     " + atributos.toString() + "\n");
        }

        return sb.toString();
    }
}
