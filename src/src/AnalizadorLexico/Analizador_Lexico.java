package AnalizadorLexico;
import AnalizadorLexico.Acciones_Semanticas.ERR;
import MainPackage.Lector_Archivo;
import AnalizadorLexico.Acciones_Semanticas.Accion_Semantica;

import java.util.ArrayList;
import java.util.Objects;

public class Analizador_Lexico {
    private ArrayList<Character> codigo;
    private int cantLineas = 1;
    private String buffer ="";
    private boolean error = false;
    private int nuevoToken;
    private Matriz_Estados matrizEstados = new Matriz_Estados();
    private Matriz_Semantica matrizSemantica = new Matriz_Semantica();
    Tabla_Simbolos tablaSimbolos = new Tabla_Simbolos();
    public static boolean isId = false;
    public Analizador_Lexico(char[] ch) {
        //Carga de codigo en array de chars
         //= Lector_Archivo.Cargar();
        //convertir array a arraylist para facilidad de uso
        codigo = new ArrayList<>();
        for (char c: ch){
            codigo.add(c);
        }
    }
    public int yylex(){
        int estActual = 0;
        String simb;
        while (estActual != -1){//-1 == estado final
            simb = String.valueOf(codigo.remove(0)); //remueve y devuelve el caracter leido en la pos 0
            if (Objects.equals(simb, "\n")){
                cantLineas++;
            }
            Accion_Semantica as = (Accion_Semantica) matrizSemantica.getCelda(estActual,simb);
            as.ejecutar(this,simb);//para despues hacer la.get...
            estActual = (int) matrizEstados.getCelda(estActual, simb); //?
            if (error){
                while (!simb.equals(",")&&!simb.equals("$")){
                    simb = String.valueOf(codigo.remove(0));
                }
                this.setBuffer("");
                if (simb.equals("$")){
                    nuevoToken = 0;
                }
                estActual = 0;
                error = false;
            }
        }
        System.out.println("Linea: " + cantLineas + " Token: " + nuevoToken + " Lexema: " + buffer);
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
        //System.out.println("Seteo token: " + tk);
        nuevoToken = tk;
    }

    public Tabla_Simbolos getTablaSimbolos(){
        return tablaSimbolos;
    }

    public int getLinea() {
        return cantLineas;
    }
    public void error(boolean err){
        error = err;
    }
}
