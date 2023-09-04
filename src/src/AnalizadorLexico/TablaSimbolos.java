package AnalizadorLexico;

import java.util.HashMap;

public class TablaSimbolos {
    private HashMap<Lexema, Token> tabla;
    public TablaSimbolos() {
        tabla = new HashMap();
    }
}
