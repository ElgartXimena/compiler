package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
//Devuelve a la entrada el último carácter leído, verifica rango de la constante y busca en TS, da de alta, y devuelve ID + puntero

public class AS8 implements Accion_Semantica {
    @Override
    public void ejecutar(String simb) {
        AS4 as4 = new AS4();
        AS6 as6 = new AS6();
        AS5 as5 = new AS5();
        as6.ejecutar(simb);
        if (!Analizador_Lexico.error){
            as4.ejecutar(simb);
            as5.ejecutar(simb);
        }
    }
}
