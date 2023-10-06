package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.*;
//- concatenar (AS2)
//- Verificar rango de la constante (AS6)
//- Alta en la TS
//- Devolver CTE + Punt TS

public class AS7 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, String simb) {
        AS2 as2 = new AS2();
        AS6 as6 = new AS6();
        as2.ejecutar(la, simb);
        as6.ejecutar(la, simb);
        Tabla_Simbolos ts = la.getTablaSimbolos();
        la.setToken(Identificador.getToken(la.getBuffer()));
        if (!ts.existeSimbolo(la.getBuffer())) {//buscar en la tabla de simbolos
            ts.insertarSimbolo(la.getBuffer(), new AtributosLexema());
        }
    }
}
