package AnalizadorLexico;

import java.util.HashMap;

public class TablaSimbolos {
    private HashMap<String, Token> tabla;
    public TablaSimbolos() {
        tabla = new HashMap();
    }
    public void insert(Token tk){
        tabla.put(tk.getLexema(),tk);
    }
    public Token getToken(String bf){
        return tabla.get(bf);
    }

    public boolean exists(String bf){
        return tabla.containsKey(bf);
    }
}
