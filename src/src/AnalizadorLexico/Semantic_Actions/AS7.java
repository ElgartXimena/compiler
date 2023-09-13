package AnalizadorLexico.Semantic_Actions;

import AnalizadorLexico.Analizador_Lexico;
import AnalizadorLexico.Tabla_Simbolos;
import AnalizadorLexico.Token;

public class AS7 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, char simb) {
        AS2 as2 = new AS2();
        AS6 as6 = new AS6();
        as2.ejecutar(la, simb);
        as6.ejecutar(la, simb);
        Token nuevo = new Token(,la.getBuffer(), la.getLinea());
        Tabla_Simbolos ts = la.getTablaSimbolos();
        ts.insertarSimbolo(nuevo);
        la.setToken(nuevo);
    }
}
