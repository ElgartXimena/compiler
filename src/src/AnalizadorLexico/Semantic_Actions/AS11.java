package AnalizadorLexico.Semantic_Actions;

import AnalizadorLexico.Lexical_Analyzer;

public class AS11 implements Semantic_Action{
    @Override
    public void execute(Lexical_Analyzer la, char simb) {
        AS4 as4 = new AS4();
        AS10 as10 = new AS10();
        as4.execute(la, simb);
        as10.execute(la, simb);
    }
}
