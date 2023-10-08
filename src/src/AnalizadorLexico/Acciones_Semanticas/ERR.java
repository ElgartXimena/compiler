package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
//AS para las celdas de la matriz que dan error
public class ERR implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, String simb) {
        la.error(true);
        System.out.println("Error en linea "+la.getLinea()+": no se esperaba simbolo "+simb);
    }
}
