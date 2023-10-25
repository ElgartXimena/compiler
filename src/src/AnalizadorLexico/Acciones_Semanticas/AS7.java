package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.*;
//Concatena, verifica rango de la constante, da de alta en la TS y devuelve CTE + Punt TS

public class AS7 implements Accion_Semantica {
    @Override
    public void ejecutar(String simb) {
        AS2 as2 = new AS2();
        AS5 as5 = new AS5();
        AS6 as6 = new AS6();
        as2.ejecutar(simb);
        as6.ejecutar(simb);
        as5.ejecutar(simb);
    }
}
