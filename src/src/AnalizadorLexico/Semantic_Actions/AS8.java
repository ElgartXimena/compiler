package AnalizadorLexico.Semantic_Actions;

import AnalizadorLexico.Analizador_Lexico;

public class AS8 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, char simb) {
        AS4 as4 = new AS4();
        AS6 as6 = new AS6();
        AS5 as5 = new AS5();
        as4.ejecutar(la, simb);
        as6.ejecutar(la, simb);
        as5.ejecutar(la, simb);
    }
}
