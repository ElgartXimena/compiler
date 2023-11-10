package GeneracionCodigoIntermedio;

import java.util.ArrayList;

public class Pila {
    private ArrayList<Object> pila;
    public Pila(Object obj){
        pila = new ArrayList<>();
        apilar(obj);
    }

    public Pila(){
        pila = new ArrayList<>();
    }
    public Object getTope(){
        return pila.get(pila.size()-1);
    }

    public void apilar(Object elemento){
        pila.add(elemento);
    }

    public Object desapilar(){
        if (pila.size()>0){
            return pila.remove(pila.size()-1);
        }
        return null;
    }

    public ArrayList<Object> getElements(){
        ArrayList<Object> out = new ArrayList<>();
        for(Object o: pila){
            out.add(o);
        }
        return out;
    }

    public String toString() {
        String out ="";
        for(Object o: pila){
            out= out.concat((String) o);
        }
        return "Pila: "+out;
    }

    public boolean pilaVacia(){
        return pila.size()==0;
    }
}
