package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
//Devolver a la entrada el ultimo caracter leido
//Buscar en la tabla de simbolos, si no esta dar de alta. Si esta, setear el token
public class AS3 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, String simb) {
        AS4 as4 = new AS4();
        AS5 as5 = new AS5();
        as4.ejecutar(la, simb);
        as5.ejecutar(la, simb);
    }
}
