package AnalizadorLexico.Semantic_Actions;

import AnalizadorLexico.Lexical_Analyzer;

//Inicializar string y añadir caracter leido
public class AS1 implements Semantic_Action {

    @Override
    public void execute(Lexical_Analyzer la, char simb) {
        la.getBuffer().substring(0,0); //limpia el buffer
        la.getBuffer().concat(String.valueOf(simb)); //inserta el simbolo
    }
}
