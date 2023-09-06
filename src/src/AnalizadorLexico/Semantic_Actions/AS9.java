package AnalizadorLexico.Semantic_Actions;

import AnalizadorLexico.Lexical_Analyzer;

public class AS9 implements Semantic_Action{
    @Override
    public void execute(Lexical_Analyzer la, char simb) {
        la.getBuffer().substring(0,0); //borra el buffer
    }
}
