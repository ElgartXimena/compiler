package GeneracionCodigoIntermedio;

import AnalizadorLexico.Tabla_Simbolos;

import java.util.ArrayList;

public class GeneradorCod {
    private static ArrayList<Terceto> tercetos = new ArrayList<>();
    public static int cantErrores = 0;
    public static int agregarTerceto(Terceto tConversion){
        tercetos.add(tConversion);
        return tercetos.size()-1;
    }
    public static int agregarTerceto(String operador, String operando1, String operando2){
        tercetos.add(new Terceto(operador,operando1,operando2));
        return tercetos.size()-1;
    }
    public static int agregarTerceto(String operador, String operando1){
        tercetos.add(new Terceto(operador,operando1));
        return tercetos.size()-1;
    }
    public static int agregarTerceto(String operador, String operando1, String operando2, String tipo){
        tercetos.add(new Terceto(operador,operando1,operando2,tipo));
        return tercetos.size()-1;
    }

    public static String to_String() {
        String out = "";
        String terceto = "";
        int i = 0;
        for(Terceto t: tercetos){
            terceto = i + " " + t.toString() + '\n';
            out = out.concat(terceto);
            i++;
        }
        return out;
    }

    public static int getIndexActual(){
        return tercetos.size()-1;
    }
}
