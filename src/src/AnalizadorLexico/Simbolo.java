package AnalizadorLexico;
//Clase creada para, en base a un simbolo de entrada, devolver la columna correspondiente en las matrices
public class Simbolo {
    public static int getColumna(int est, String sim){
        if (sim.equals("u") && est == 3) //entero largo sin signo _ul
            return 6;

        if (sim.equals("l") && est == 4) //entero largo sin signo _ul
            return 9;

        if (sim.equals("s") && est == 3) //entero corto _s
            return 7;

        if (sim.equals("d") && est == 6) //punto flotante .d
            return 8;

        if (sim.equals("D") && est == 6) //punto flotante .D
            return 10;

        if (Character.isUpperCase(sim.charAt(0))) {
            return 1;
        }

        if (Character.isLowerCase(sim.charAt(0))) {
            return 2;
        }

        if (Character.isDigit(sim.charAt(0))) {
            return 3;
        }

        switch (sim.charAt(0)) {
            case ':':
            case ';':
            case ',':
            case '(':
            case ')':
            case '{':
            case '}':
                return 0;
            case '_': return 4;
            case '.': return 5;
            case '\n':
            case '\r':
                return 11;
            case 32: return 12;     // Espacio en blanco (ASCII)
            case 9: return 13;      // Tabulación (ASCII)
            case '/': return 14;
            case '*': return 15;
            case '+': return 16;
            case '-': return 17;
            case '=': return 18;
            case '<': return 19;
            case '>': return 20;
            case '%': return 21;
            case '!': return 22;
            case '$': return 24;
            default: return 23; //Otros simbolos
        }

    }
}
