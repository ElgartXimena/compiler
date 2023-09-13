package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;

public class AS9 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, char simb) {
        la.getBuffer().substring(0,0); //borra el buffer
    }
}
