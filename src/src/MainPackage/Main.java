package MainPackage;

import AnalizadorLexico.Analizador_Lexico;

public class Main {
    public static void main(String[] args) {
        Analizador_Lexico la = new Analizador_Lexico();
        la.getToken();
        System.exit(0);
    }
}