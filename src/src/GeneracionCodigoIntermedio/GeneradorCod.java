package GeneracionCodigoIntermedio;

import AnalizadorLexico.Tabla_Simbolos;

import java.util.ArrayList;

public class GeneradorCod {
    private static ArrayList<Terceto> tercetos = new ArrayList<>();

    public static void agregarTerceto(String operador, String operando1, String operando2){

        tercetos.add(new Terceto(operador, operando1, operando2, Tabla_Simbolos.getAtributos(operando1).getTipo()));
    }

    public static boolean chequearTipos(String operador, String operando1, String operando2){
        Terceto t = Conversor.convertir(operador, operando1,operando2);
        if (t!=null){
            tercetos.add(t);
            return true;
        }
        return false;
    }

}
