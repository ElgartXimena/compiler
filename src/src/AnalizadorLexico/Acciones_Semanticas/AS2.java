package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
//Concatena el simbolo al string existente
public class AS2 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, String simb) {

        la.setBuffer(la.getBuffer().concat(simb));
    }
}
