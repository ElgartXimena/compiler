package AnalizadorLexico;
//Clase creada para, en base a un simbolo de entrada, devolver la columna correspondiente en las matrices
public class Simbolo {
    public static int getColumna(char sim){

        if (Character.isUpperCase(sim))
            return 1;

        if (Character.isLowerCase(sim))
            return 2;

        switch (sim) {
            case ';':
            case ',':
            case '(':
            case ')':
            case '{':
            case '}':
                return 0;
            case '_': return 4;
            case '.': return 5;
            case '\n': return 11;
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
        }

        return 23; //Otros simbolos


    }
}
