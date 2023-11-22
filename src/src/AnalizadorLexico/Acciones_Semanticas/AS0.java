package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;

//AS para fin de archivo
public class AS0 implements Accion_Semantica{
    @Override
    public void ejecutar(String simb) {
        Analizador_Lexico.setToken(0);
    }
}
