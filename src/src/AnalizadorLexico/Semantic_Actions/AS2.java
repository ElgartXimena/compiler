package AnalizadorLexico.Semantic_Actions;

import AnalizadorLexico.Analizador_Lexico;
//concatena el simbolo al string existente
public class AS2 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, char simb) {
        la.getBuffer().concat(String.valueOf(simb));
    }
}
