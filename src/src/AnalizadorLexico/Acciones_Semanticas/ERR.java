package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;

public class ERR implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, String simb) {
        System.out.println("Error en linea "+la.getLinea()+": no se esperaba simbolo "+simb);
    }
}
