package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
//Devolver a la entrada el ultimo caracter leido

public class AS4 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, char simb) {
        la.getCodigo().add(0,simb);
    }
}
