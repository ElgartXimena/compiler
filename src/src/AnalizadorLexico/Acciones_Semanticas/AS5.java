package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.*;
//Busca en la tabla de simbolos, si no esta da de alta. Si esta, setea el token
public class AS5 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, String simb) {
        Tabla_Simbolos ts = la.getTablaSimbolos();
        la.setToken(Identificador.getToken(la));
        if (!ts.existeSimbolo(la.getBuffer())) {//buscar en la tabla de simbolos
            ts.insertarSimbolo(la.getBuffer(), new AtributosLexema());
        }
    }
}
