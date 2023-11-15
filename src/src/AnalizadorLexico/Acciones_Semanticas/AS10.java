package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
import AnalizadorLexico.Tabla_Palabras_Reservadas;
//Chequeo en tabla de palabras reservadas
public class AS10 implements Accion_Semantica {
    @Override
    public void ejecutar(String simb) {
        if (Tabla_Palabras_Reservadas.existePR(Analizador_Lexico.buffer)){
            Analizador_Lexico.setToken(Tabla_Palabras_Reservadas.getTokenPR(Analizador_Lexico.buffer));
        } else {
            System.out.println("Linea: " + Analizador_Lexico.cantLineas + " No existe la palabra reservada " + Analizador_Lexico.buffer);
        }
    }
}
