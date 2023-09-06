package AnalizadorLexico.Semantic_Actions;

import AnalizadorLexico.Lexical_Analyzer;

public class ERR implements Semantic_Action{
    @Override
    public void execute(Lexical_Analyzer la, char simb) {
        System.out.println("Error en linea "+la.getLinea()+": no se esperaba simbolo "+simb);
    }
}
