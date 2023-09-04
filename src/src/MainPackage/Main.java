package MainPackage;

import AnalizadorLexico.Lexical_Analyzer;

public class Main {
    public static void main(String[] args) {
        Lexical_Analyzer la = new Lexical_Analyzer();
        la.getToken();
        System.exit(0);
    }
}