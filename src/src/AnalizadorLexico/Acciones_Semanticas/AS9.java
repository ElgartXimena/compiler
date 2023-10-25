package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
//Descarta el buffer
public class AS9 implements Accion_Semantica {
    @Override
    public void ejecutar(String simb) {
        Analizador_Lexico.setBuffer(""); //borra el buffer
    }
}
