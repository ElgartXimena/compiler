package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
import AnalizadorLexico.Token;

public class AS0 implements Accion_Semantica{
    @Override
    public void ejecutar(Analizador_Lexico la, char simb) {
        la.setToken(new Token(0,"",la.getLinea()));
    }
}
