package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;

//Inicializar string y añadir caracter leido
public class AS1 implements Accion_Semantica {

    @Override
    public void ejecutar(Analizador_Lexico la, String simb) {
        la.setBuffer(""); //limpia el buffer
        la.setBuffer(la.getBuffer().concat(simb)); //inserta el simbolo
    }
}
