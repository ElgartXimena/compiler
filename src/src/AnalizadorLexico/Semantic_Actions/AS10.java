package AnalizadorLexico.Semantic_Actions;

import AnalizadorLexico.Lexical_Analyzer;
import AnalizadorLexico.TablaPalabrasReservadas;

public class AS10 implements Semantic_Action{
    @Override
    public void execute(Lexical_Analyzer la, char simb) {
        TablaPalabrasReservadas.existePR(la.getBuffer());
    }
}
