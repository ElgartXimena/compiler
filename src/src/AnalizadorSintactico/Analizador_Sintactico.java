package AnalizadorSintactico;

import AnalizadorLexico.Analizador_Lexico;
import MainPackage.Main;
import AnalizadorLexico.Tabla_Simbolos;

public class Analizador_Sintactico {
    private Analizador_Lexico al;
    private Parser parser;
    public Analizador_Sintactico(char[] codigo){
        al = new Analizador_Lexico(codigo);
        parser = new Parser();
    }

    public void iniciar(){
        parser.run();
        System.out.println(Tabla_Simbolos.to_String());
    }
}
