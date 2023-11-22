package GeneracionCodigoIntermedio;

import AnalizadorLexico.Tabla_Simbolos;

import java.util.ArrayList;
import java.util.HashMap;

public class GeneradorCod {
    private static ArrayList<Terceto> tercetos = new ArrayList<>();
    public static int cantErrores = 0;

    private static Pila flagFuncion = new Pila();
    private static HashMap<String, ArrayList<Terceto>> tercetosFuncion = new HashMap<>();
    public static int agregarTerceto(Terceto tConversion){
        return insertarTerceto(tConversion);
    }
    public static int agregarTerceto(String operador, String operando1, String operando2){
        return insertarTerceto(new Terceto(operador,operando1,operando2));
    }
    public static int agregarTerceto(String operador, String operando1){
        return insertarTerceto(new Terceto(operador,operando1));
    }
    public static int agregarTerceto(String operador, String operando1, String operando2, String tipo){
        return insertarTerceto(new Terceto(operador,operando1,operando2, tipo));
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
        for (String key: tercetosFuncion.keySet()){
            terceto = "FUNCION: "+key + '\n';
            out = out.concat(terceto);
            i=0;
            for (Terceto t: tercetosFuncion.get(key)){
                terceto = i + " " + t.toString() + '\n';
                out = out.concat(terceto);
                i++;
            }
        }
        return out;
    }

    public static int getIndexActual(){
        if (flagFuncion.pilaVacia()){
            return tercetos.size()-1;
        } else {
            return tercetosFuncion.get(flagFuncion.getTope()).size()-1; //retorna el indice actual de la lista de terceto asociados a una funcion
        }
    }

    public static int agregarTercetoLabel(){
        int index = getIndexActual()+1;
        if (flagFuncion.pilaVacia()){
            return insertarTerceto(new Terceto("LABEL",String.valueOf(index)));
        } else {
            return insertarTerceto(new Terceto("LABEL",flagFuncion.getTope()+String.valueOf(index)));
        }
    }

    public static void setFlagFuncion(String nombreFuncion){
        flagFuncion.apilar(nombreFuncion);
        tercetosFuncion.put(nombreFuncion, new ArrayList<Terceto>());
    }

    public static void borrarFlag(){
        flagFuncion.desapilar();
    }

    private static int insertarTerceto(Terceto t){

        if (flagFuncion.pilaVacia()){
            tercetos.add(t);
            return tercetos.size()-1;
        } else {
            ArrayList<Terceto> arr = tercetosFuncion.get(flagFuncion.getTope());
            arr.add(t);
            return arr.size()-1;
        }
    }

    public static String getTipoTerceto(int nro){
        if(flagFuncion.pilaVacia()){
            return tercetos.get(nro).getTipo();
        }
        return tercetosFuncion.get(flagFuncion.getTope()).get(nro).getTipo();
    }

    public static ArrayList<Terceto> getTercetos(){
        return tercetos;
    }
    public static HashMap<String, ArrayList<Terceto>> getTercetosFuncion(){
        return tercetosFuncion;
    }
}
