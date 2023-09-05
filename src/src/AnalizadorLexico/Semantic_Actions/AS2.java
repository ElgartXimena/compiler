package AnalizadorLexico.Semantic_Actions;

import AnalizadorLexico.Lexical_Analyzer;
//concatena el simbolo al string existente
public class AS2 implements Semantic_Action{
    @Override
    public void execute(Lexical_Analyzer la, char simb) {
        la.getBuffer().concat(String.valueOf(simb));
    }
}
