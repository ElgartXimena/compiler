package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.*;

public class AS5 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, char simb) {
        Tabla_Simbolos ts = la.getTablaSimbolos();
        if (!ts.existeSimbolo(la.getBuffer())) {//buscar en la tabla de simbolos
            ts.insertarSimbolo(la.getBuffer(), new AtributosLexema());
        }
        la.setToken(Identificador.getToken(la.getBuffer()));
    }
}
