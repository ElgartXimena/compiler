package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
//Concatena el simbolo al string existente
public class AS2 implements Accion_Semantica {
    @Override
    public void ejecutar(String simb) {

        Analizador_Lexico.setBuffer(Analizador_Lexico.buffer.concat(simb));
    }
}
