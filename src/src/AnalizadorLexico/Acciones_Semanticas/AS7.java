package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.*;
//Concatena, verifica rango de la constante, da de alta en la TS y devuelve CTE + Punt TS

public class AS7 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, String simb) {
        AS2 as2 = new AS2();
        AS6 as6 = new AS6();
        as2.ejecutar(la, simb);
        as6.ejecutar(la, simb);
        Tabla_Simbolos ts = la.getTablaSimbolos();
        //System.out.print("Linea: " + la.getLinea());
        la.setToken(Identificador.getToken(la.getBuffer()));
        if (!ts.existeSimbolo(la.getBuffer())) {//buscar en la tabla de simbolos
            ts.insertarSimbolo(la.getBuffer(), new AtributosLexema());
        }
    }
}
