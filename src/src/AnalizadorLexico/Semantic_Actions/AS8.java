package AnalizadorLexico.Semantic_Actions;

import AnalizadorLexico.Lexical_Analyzer;

public class AS8 implements Semantic_Action{
    @Override
    public void execute(Lexical_Analyzer la, char simb) {
        AS4 as4 = new AS4();
        AS6 as6 = new AS6();
        AS5 as5 = new AS5();
        as4.execute(la, simb);
        as6.execute(la, simb);
        as5.execute(la, simb);
    }
}
