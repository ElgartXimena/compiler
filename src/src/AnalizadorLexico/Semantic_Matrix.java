package AnalizadorLexico;

import AnalizadorLexico.Semantic_Actions.Semantic_Action;

import java.util.ArrayList;
import java.util.List;

public class Semantic_Matrix {
    private Semantic_Action[][] matriz_semantica;
    private ArrayList<Character> simbolos = new ArrayList(List.of("{","}",";",",","(",")"));
    public Semantic_Matrix() {
        this.matriz_semantica = new Semantic_Action[][]{
                {/*accionsemantica, accionsemantica,....,*/},
                {},
                {}
        };
    }
    public Semantic_Action getAS(int estadoActual, char sActual){
        int symbolIndex = simbolos.indexOf(sActual);
        return matriz_semantica[estadoActual][symbolIndex];
    }

}
