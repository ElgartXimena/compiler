package AnalizadorLexico.Semantic_Actions;

import AnalizadorLexico.Analizador_Lexico;
import AnalizadorLexico.Tabla_Simbolos;
import AnalizadorLexico.Token;

public class AS5 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, char simb) {
        Tabla_Simbolos ts = la.getTablaSimbolos();
        if (ts.existeSimbolo(la.getBuffer())) {//buscar en la tabla de simbolos
            la.setToken(ts.getToken(la.getBuffer()));
            //si esta, devuelve el token. En este caso lo que hacemos es setear el token
            //en el analizador lexico, que es lo que retorna en la funcion getToken()
        } else {
            Token nuevo = new Token(,la.getBuffer(), la.getLinea());
            ts.insertarSimbolo(nuevo);
            la.setToken(nuevo);
        }
    }
}
