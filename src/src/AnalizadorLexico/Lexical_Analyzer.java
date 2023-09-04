package AnalizadorLexico;
import MainPackage.File_Loader;
import AnalizadorLexico.Semantic_Actions.Semantic_Action;

import java.util.ArrayList;
import java.util.HashMap;

public class Lexical_Analyzer {
    private ArrayList<Character> code;
    private int lines = 1;
    private String buffer ="";
    private State_Matrix stateMatrix = new State_Matrix();
    private Semantic_Matrix semanticMatrix = new Semantic_Matrix();
    TablaSimbolos tablaSimbolos = new TablaSimbolos();

    public Lexical_Analyzer() {
        //Carga de codigo en array de chars
        char[] ch = File_Loader.Load();
        //convertir array a arraylist para facilidad de uso
        for (char c: ch){
            code.add(c);
        }
    }
    public void getToken(){
        int estActual = 0;
        char simb;
        while (estActual != -1){//-1 == estado final
            simb = code.remove(0); //remueve y devuelve el caracter leido en la pos 0
            if (simb == '\n'){
                lines++;
            }
            Semantic_Action as = semanticMatrix.getAS(estActual,simb);
            as.execute(this);//para despues hacer la.get...
            estActual = stateMatrix.getNextState(estActual, simb); //?
        }
        return new Token(lines, buffer, ); //??
    }
}
