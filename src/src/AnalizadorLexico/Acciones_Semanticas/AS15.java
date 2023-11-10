package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;

//Setea el token e inserta en la TS
public class AS15 implements Accion_Semantica {
    @Override
    public void ejecutar(String simb) {
        AS2 as2 = new AS2();
        AS5 as5 = new AS5();
        as2.ejecutar(simb);
        Analizador_Lexico.buffer = Analizador_Lexico.buffer.replaceAll("([\n\r])"," ");
        while (Analizador_Lexico.buffer.contains("  ")){
            Analizador_Lexico.buffer = Analizador_Lexico.buffer.replaceAll("    "," ");
        }
        as5.ejecutar(simb);
    }
}