package AnalizadorLexico.Semantic_Actions;

import AnalizadorLexico.Lexical_Analyzer;
import AnalizadorLexico.TablaSimbolos;
import AnalizadorLexico.Token;

public class AS5 implements Semantic_Action{
    @Override
    public void execute(Lexical_Analyzer la, char simb) {
        TablaSimbolos ts = la.getTablaSimbolos();
        if (ts.exists(la.getBuffer())) {//buscar en la tabla de simbolos
            la.setToken(ts.getToken(la.getBuffer()));
            //si esta, devuelve el token. En este caso lo que hacemos es setear el token
            //en el analizador lexico, que es lo que retorna en la funcion getToken()
        } else {
            Token nuevo = new Token(0,la.getBuffer(), la.getLinea());
            ts.insert(nuevo);
            la.setToken(nuevo);
        }
    }
}
