package AnalizadorLexico;

import AnalizadorLexico.Semantic_Actions.Semantic_Action;

public class Semantic_Matrix extends Matrix{
    private Semantic_Action[][] matriz_semantica;
    public Semantic_Matrix() {
        this.matriz_semantica = new Semantic_Action[][]{
                {/*accionsemantica, accionsemantica,....,*/},
                {},
                {}
        };
    }
}
