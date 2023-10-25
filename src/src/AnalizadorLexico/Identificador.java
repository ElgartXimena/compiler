package AnalizadorLexico;

public class Identificador {
    //recibe un lexema y devuelve que token le corresponde

    private static final String ID = "^[a-z_].*";
    private static final String CTE = "^[0-9.].*";
    private static final String CADENA = "^%[\\s\\S]*%$";

    public static int getToken() {
        //System.out.println("Lexema: " + lexema);
        String lexema = Analizador_Lexico.buffer;
        int linea = Analizador_Lexico.cantLineas;
        switch (lexema) {
            case "(": return 40;
            case ")": return 41;
            case "*": return 42;
            case "{": return 123;
            case "}": return 125;
            case ":": return 58;
            case ";": return 59;
            case ".": return 46;
            case ",": return 44;
            case "/": return 47;
            case "+": return 43;
            case "-": return 45;
            case "<": return 60;
            case ">": return 62;
            case "=": return 61;
            case "-=": return 276;
            case ">=": return 277;
            case "<=": return 278;
            case "==": return 279;
            case "!!": return 280;
            default:
                if (lexema.matches(ID)) {
                    System.out.println("Linea: " + linea + " Se reconocio un IDENTIFICADOR");
                    if (lexema.length() > 20){
                        System.out.print("Linea: " + linea + " WARNING: "+lexema+" excede los 20 caracteres.");
                        lexema = lexema.substring(0,19);
                        Analizador_Lexico.setBuffer(lexema);
                        System.out.println(" Se ha truncado a: "+lexema);
                    }
                    return 257;
                } else if (lexema.matches(CTE)) {
                    return 258;
                } else if (lexema.matches(CADENA)) {
                    System.out.println("Linea: " + linea + " Se reconocio una CADENA");
                    return 259;
                }
                return -1;
        }
    }
}
