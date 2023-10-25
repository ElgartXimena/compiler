package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
//AS para las celdas de la matriz que dan error
public class ERR implements Accion_Semantica {
    @Override
    public void ejecutar(String simb) {
        Analizador_Lexico.setError(true);
        System.out.println("Error en linea "+Analizador_Lexico.cantLineas+": no se esperaba simbolo "+simb);
    }
}
