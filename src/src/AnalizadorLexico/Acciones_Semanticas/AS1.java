package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;

//Inicializa string y añade caracter leido
public class AS1 implements Accion_Semantica {

    @Override
    public void ejecutar(String simb) {
        Analizador_Lexico.setBuffer(""); //limpia el buffer
        Analizador_Lexico.setBuffer(Analizador_Lexico.buffer.concat(simb)); //inserta el simbolo
    }
}
