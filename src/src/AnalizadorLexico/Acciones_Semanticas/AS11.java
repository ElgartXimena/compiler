package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
//Devuelve a la entrada el ultimo caracter leido y chequea palabra reservada
public class AS11 implements Accion_Semantica {
    @Override
    public void ejecutar(String simb) {
        AS4 as4 = new AS4();
        AS10 as10 = new AS10();
        as4.ejecutar(simb);
        as10.ejecutar(simb);
    }
}
