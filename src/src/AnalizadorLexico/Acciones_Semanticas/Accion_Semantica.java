package AnalizadorLexico.Acciones_Semanticas;
import AnalizadorLexico.Analizador_Lexico;
public interface Accion_Semantica {

    void ejecutar(Analizador_Lexico la, String simb);

}
