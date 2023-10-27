package GeneracionCodigoIntermedio;

import java.util.ArrayList;

public class Pila {
    protected static ArrayList<Object> pila;
    public Pila(Object obj){
        pila = new ArrayList<>();
        insertar(obj);
    }

    public Pila(){
        pila = new ArrayList<>();
    }
    public static Object getTope(){
        return pila.get(pila.size()-1);
    }

    public static void insertar(Object elemento){
        pila.add(elemento);
    }

    public static void eliminarTope(){
        pila.remove(pila.size()-1);
    }

    public static ArrayList<Object> getElements(){
        ArrayList<Object> out = new ArrayList<>();
        for(Object o: pila){
            out.add(o);
        }
        return out;
    }
}
