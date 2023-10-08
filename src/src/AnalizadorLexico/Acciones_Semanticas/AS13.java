package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
//Concatena y devuelve el token
public class AS13 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, String simb) {
        AS2 as2 = new AS2();
        AS12 as12 = new AS12();
        as2.ejecutar(la, simb);
        as12.ejecutar(la, la.getBuffer());
    }
}
