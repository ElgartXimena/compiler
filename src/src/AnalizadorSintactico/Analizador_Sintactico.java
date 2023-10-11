package AnalizadorSintactico;

import AnalizadorLexico.Analizador_Lexico;
import AnalizadorLexico.Tabla_Simbolos;

public class Analizador_Sintactico {
    private Analizador_Lexico al;
    private Parser parser;
    public Analizador_Sintactico(char[] codigo){
        al = new Analizador_Lexico(codigo);
        parser = new Parser();
    }

    public void iniciar(){
        parser.run(this.al);
        System.out.println(al.getTablaSimbolos().toString());
    }
}
