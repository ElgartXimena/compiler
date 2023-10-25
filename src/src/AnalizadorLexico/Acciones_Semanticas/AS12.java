package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
import AnalizadorLexico.Identificador;
//Devuelve el token para los que vayan del estado 0 a F
public class AS12 implements Accion_Semantica {
    @Override
    public void ejecutar(String simb) {
        AS1 as1 = new AS1();
        as1.ejecutar(simb);
        Analizador_Lexico.setToken(Identificador.getToken());
    }
}
