package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
//Devuelve a la entrada el ultimo caracter leido

public class AS4 implements Accion_Semantica {
    @Override
    public void ejecutar(String simb) {
        Analizador_Lexico.devolverSimbolo(0,simb.charAt(0));
    }
}
