package AnalizadorLexico;
import MainPackage.Lector_Archivo;
import AnalizadorLexico.Acciones_Semanticas.Accion_Semantica;

import java.util.ArrayList;

public class Analizador_Lexico {
    private ArrayList<Character> codigo;
    private int cantLineas = 1;
    private String buffer ="";
    private int nuevoToken;
    private Matriz_Estados matrizEstados = new Matriz_Estados();
    private Matriz_Semantica matrizSemantica = new Matriz_Semantica();
    Tabla_Simbolos tablaSimbolos = new Tabla_Simbolos();

    public Analizador_Lexico() {
        //Carga de codigo en array de chars
        char[] ch = Lector_Archivo.Cargar();
        //convertir array a arraylist para facilidad de uso
        codigo = new ArrayList<>();
        for (char c: ch){
            codigo.add(c);
        }
    }
    public int yylex(){
        int estActual = 0;
        char simb;
        while (estActual != -1){//-1 == estado final
            System.out.println("Estado actual: " + estActual);
            System.out.println("Lex: " + buffer);
            simb = codigo.remove(0); //remueve y devuelve el caracter leido en la pos 0
            if (simb == '\n'){
                cantLineas++;
            }
            System.out.println("simb: " + simb);
            Accion_Semantica as = (Accion_Semantica) matrizSemantica.getCelda(estActual,simb);
            as.ejecutar(this,simb);//para despues hacer la.get...
            estActual = (int) matrizEstados.getCelda(estActual, simb); //?
        }
        return nuevoToken;

    }

    public String getBuffer(){
        return buffer;
    }

    public void setBuffer(String buffer) {
        this.buffer = buffer;
    }

    public ArrayList getCodigo(){
        return codigo;
    }

    public void setToken(int tk){
        nuevoToken = tk;
    }

    public Tabla_Simbolos getTablaSimbolos(){
        return tablaSimbolos;
    }

    public int getLinea() {
        return cantLineas;
    }
}
