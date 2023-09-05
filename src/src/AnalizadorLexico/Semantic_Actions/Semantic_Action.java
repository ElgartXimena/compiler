package AnalizadorLexico.Semantic_Actions;
import AnalizadorLexico.Lexical_Analyzer;
public interface Semantic_Action {

    void execute(Lexical_Analyzer la, char simb);
}
