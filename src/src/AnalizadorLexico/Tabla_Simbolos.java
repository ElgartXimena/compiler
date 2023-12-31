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
    public static boolean modificarClave(String lexemaOld, String lexemaNew){
        AtributosLexema at = tabla.remove(lexemaOld);
        if (!tabla.containsKey(lexemaNew)){
            tabla.put(lexemaNew,at);
            return true;
        }
        return false;
    }
    public static void borrarSimbolo(String lex){
        tabla.remove(lex);
    }
    public static boolean existeSimbolo(String bf){
        return tabla.containsKey(bf);
    }
    public static String to_String() {
        StringBuilder sb = new StringBuilder();
        sb.append("|" + String.format("%-24s", "    TABLA DE SIMBOLOS   ") + "|" + "\n");
        sb.append("|" + String.format("%-24s", "         Lexema         "));
        sb.append("|" + String.format("%-10s", "   Tipo   "));
        sb.append("|" + String.format("%-10s", "   Uso    "));
        sb.append("|" + String.format("%-20s", "     Implementa     "));
        sb.append("|" + String.format("%-20s", "       Hereda       "));
        sb.append("|" + String.format("%-16s", " isImplementado "));
        sb.append("|" + String.format("%-24s", "       Parametro        "));
        sb.append("|" + String.format("%-10s", "Tipo Param")+"|"+"\n");
        sb.append("|------------------------|----------|----------|--------------------|--------------------|----------------|------------------------|----------|"+"\n");

        for (String lexema : tabla.keySet()) {
            sb.append("|" + String.format("%-24s", lexema));
            sb.append("|" + String.format("%-10s", getAtributos(lexema).getTipo()));
            sb.append("|" + String.format("%-10s", getAtributos(lexema).getUso()));
            sb.append("|" + String.format("%-20s", getAtributos(lexema).getImplementa()));
            sb.append("|" + String.format("%-20s", getAtributos(lexema).getHereda()));
            sb.append("|" + String.format("%-16s", getAtributos(lexema).isImplementado()));
            sb.append("|" + String.format("%-24s", getAtributos(lexema).getParametro()));
            sb.append("|" + String.format("%-10s", getAtributos(lexema).getTipoParametro())+"|"+"\n");
        }

        return sb.toString();
    }

    public static HashMap<String, String> getVariables(){
        HashMap<String, String> out = new HashMap<>();
        for (String lexema : tabla.keySet()){
            if (getAtributos(lexema).isUso("VARIABLE") || getAtributos(lexema).isUso("PARAMETRO")){
                out.put(lexema, getAtributos(lexema).getTipo());
            }
        }
        return out;
    }
}
