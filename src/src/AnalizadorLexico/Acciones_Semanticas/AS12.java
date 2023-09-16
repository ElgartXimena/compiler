package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
import AnalizadorLexico.Identificador;
//para los que vayan de 0 a F
public class AS12 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, String simb) {
        la.setToken(Identificador.getToken(simb));
    }
}
