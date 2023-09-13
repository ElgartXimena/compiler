package AnalizadorLexico.Semantic_Actions;
import AnalizadorLexico.Analizador_Lexico;
public interface Accion_Semantica {

    void ejecutar(Analizador_Lexico la, char simb);
}
