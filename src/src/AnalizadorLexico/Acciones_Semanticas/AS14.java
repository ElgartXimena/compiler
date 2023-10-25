package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
//Devuelve a la entrada el ultimo caracter leido y devuelve el token
public class AS14 implements Accion_Semantica {
    @Override
    public void ejecutar(String simb) {
        AS4 as4 = new AS4();
        AS12 as12 = new AS12();
        as4.ejecutar(simb);
        as12.ejecutar(Analizador_Lexico.buffer);
    }
}