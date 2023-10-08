package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;

//AS para fin de archivo
public class AS0 implements Accion_Semantica{
    @Override
    public void ejecutar(Analizador_Lexico la, String simb) {
        System.out.println("Fin de archivo");
        la.setToken(0);
    }
}
