package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
//descarta el buffer
public class AS9 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, String simb) {
        la.setBuffer(""); //borra el buffer
    }
}
