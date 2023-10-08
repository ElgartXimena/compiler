package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
import AnalizadorLexico.Tabla_Palabras_Reservadas;
//Chequeo en tabla de palabras reservadas
public class AS10 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, String simb) {
        if (Tabla_Palabras_Reservadas.existePR(la.getBuffer())){
            la.setToken(Tabla_Palabras_Reservadas.getTokenPR(la.getBuffer()));
            System.out.println("Linea: " + la.getLinea() + " Palabra reservada: " + la.getBuffer());
        } else {
            System.out.println("Linea: " + la.getLinea() + " No existe la palabra reservada " + la.getBuffer());
        }
    }
}
