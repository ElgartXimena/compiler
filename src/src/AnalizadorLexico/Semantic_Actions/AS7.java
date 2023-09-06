package AnalizadorLexico.Semantic_Actions;

import AnalizadorLexico.Lexical_Analyzer;
import AnalizadorLexico.TablaSimbolos;
import AnalizadorLexico.Token;

public class AS7 implements Semantic_Action{
    @Override
    public void execute(Lexical_Analyzer la, char simb) {
        AS2 as2 = new AS2();
        AS6 as6 = new AS6();
        as2.execute(la, simb);
        as6.execute(la, simb);
        Token nuevo = new Token(,la.getBuffer(), la.getLinea());
        TablaSimbolos ts = la.getTablaSimbolos();
        ts.insert(nuevo);
        la.setToken(nuevo);
    }
}
