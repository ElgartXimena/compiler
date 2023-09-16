package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
//Devolver a la entrada el ultimo caracter leido
//chequear palabra reservada
public class AS11 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, String simb) {
        AS4 as4 = new AS4();
        AS10 as10 = new AS10();
        as4.ejecutar(la, simb);
        as10.ejecutar(la, simb);
    }
}
