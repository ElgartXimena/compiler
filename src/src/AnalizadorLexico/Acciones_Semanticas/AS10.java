package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
import AnalizadorLexico.Tabla_Palabras_Reservadas;

public class AS10 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, char simb) {
        Tabla_Palabras_Reservadas.existePR(la.getBuffer());
    }
}
