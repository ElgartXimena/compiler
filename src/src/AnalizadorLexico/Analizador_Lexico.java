package AnalizadorLexico;
import AnalizadorLexico.Acciones_Semanticas.Accion_Semantica;

import java.util.ArrayList;

public class Analizador_Lexico {
    private static ArrayList<Character> codigo;
    public static int cantLineas = 1;
    public static String buffer ="";
    public static boolean error = false;
    private static int nuevoToken;
    private static Matriz_Estados matrizEstados = new Matriz_Estados();
    private static Matriz_Semantica matrizSemantica = new Matriz_Semantica();

    public Analizador_Lexico(char[] ch) {
        //Carga de codigo en array de chars
         //= Lector_Archivo.Cargar();
        //convertir array a arraylist para facilidad de uso
        codigo = new ArrayList<>();
        for (char c: ch){
            codigo.add(c);
        }
    }
    public static int yylex(){
        int estActual = 0;
        String simb = "";
        while (estActual != -1){//-1 == estado final
            simb = String.valueOf(codigo.remove(0)); //remueve y devuelve el caracter leido en la pos 0
            if (simb.equals("\n")) {
                cantLineas++;
            }
            Accion_Semantica as = (Accion_Semantica) matrizSemantica.getCelda(estActual,simb);
            as.ejecutar(simb);//para despues hacer la.get...
            estActual = (int) matrizEstados.getCelda(estActual, simb); //?
            if (error){
                while (!simb.equals(",")&&!simb.equals("$")){
                    simb = String.valueOf(codigo.remove(0));
                }
                setBuffer("");
                if (simb.equals("$")){
                    nuevoToken = 0;
                    estActual = -1;
                } else {
                    estActual = 0;
                    codigo.add(0, simb.charAt(0));
                }
                error = false;
            }
        }
        if (!simb.equals("$")) {
            System.out.println("Linea: " + cantLineas + " Token: " + nuevoToken + " Lexema: " + buffer);
        }
        return nuevoToken;
    }

    public String getBuffer(){
        return buffer;
    }

    public static void setBuffer(String bf) {
        buffer = bf;
    }

    public static void devolverSimbolo(int index, char simb){
        codigo.add(index,simb);
    }

    public static void setToken(int tk){
        nuevoToken = tk;
    }

    public static void setError(boolean err){
        error = err;
    }
    public static boolean isError(){
        return error;
    }
}
