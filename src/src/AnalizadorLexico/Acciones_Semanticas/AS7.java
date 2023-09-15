package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.*;

public class AS7 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, char simb) {
        AS2 as2 = new AS2();
        AS6 as6 = new AS6();
        as2.ejecutar(la, simb);
        as6.ejecutar(la, simb);
        Tabla_Simbolos ts = la.getTablaSimbolos();
        ts.insertarSimbolo(la.getBuffer(), new AtributosLexema());
        la.setToken(Identificador.getToken(la.getBuffer()));
    }
}
