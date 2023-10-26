package AnalizadorLexico;

import java.util.HashMap;

public final class Tabla_Simbolos {
    private static HashMap<String, AtributosLexema> tabla = new HashMap<>();
    public static void insertarSimbolo(String lexema, AtributosLexema at){
            tabla.put(lexema,at);
    }
    public static AtributosLexema getAtributos(String lexema){
        return tabla.get(lexema);
    }
    public static void modificarClave(String lexemaOld, String lexemaNew){
        AtributosLexema at = tabla.remove(lexemaOld);
        if (!tabla.containsKey(lexemaNew)){
            tabla.put(lexemaNew,at);
        }
    }
    public static boolean existeSimbolo(String bf){
        return tabla.containsKey(bf);
    }
    public static String to_String() {
        StringBuilder sb = new StringBuilder();
        sb.append("|" + String.format("%-24s", "    TABLA DE SIMBOLOS   ") + "|" + "\n");
        sb.append("|" + String.format("%-24s", "         Lexema         ") + "|"+String.format("%-8s", "  Tipo  ")+"|" + "\n");
        sb.append("|------------------------|--------|\n");

        for (String lexema : tabla.keySet()) {
            sb.append("|" + String.format("%-24s", lexema) + "|" +String.format("%-8s", getAtributos(lexema).getTipo())+"|" + "\n");
        }

        return sb.toString();
    }
}
