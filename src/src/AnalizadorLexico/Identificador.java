package AnalizadorLexico;

public class Identificador {
    //recibe un lexema y devuelve que token le corresponde

    private static final String ID = "^[a-z_].*";
    private static final String CTE = "^[0-9.].*";
    private static final String CADENA = "^%[\\s\\S]*%$";

    public static int getToken(String lexema) {
        System.out.println("Lexema: " + lexema);

        switch (lexema) {
            case "(": return 40;
            case ")": return 41;
            case "{": return 123;
            case "}": return 125;
            case ":": return 58;
            case ";": return 59;
            case ",": return 44;
            case "/": return 47;
            case "+": return 43;
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
                    return 257;
                } else if (lexema.matches(CTE)) {
                    return 258;
                } else if (lexema.matches(CADENA)) {
                    return 259;
                }
                return -1;
        }
    }
}
