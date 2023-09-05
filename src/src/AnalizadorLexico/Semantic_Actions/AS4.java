package AnalizadorLexico.Semantic_Actions;

import AnalizadorLexico.Lexical_Analyzer;
//Devolver a la entrada el ultimo caracter leido

public class AS4 implements Semantic_Action{
    @Override
    public void execute(Lexical_Analyzer la, char simb) {
        la.getCode().add(0,simb);
    }
}
