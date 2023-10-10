package AnalizadorSintactico;

import AnalizadorLexico.Analizador_Lexico;

public class Analizador_Sintactico {
    private Analizador_Lexico al;
    private Parser parser;
    public Analizador_Sintactico(char[] codigo){
        al = new Analizador_Lexico(codigo);
        parser = new Parser(true);
    }

    public void iniciar(){
        parser.run(this.al);
    }
}
