package GeneracionCodAssembler;

import AnalizadorLexico.Tabla_Simbolos;
import GeneracionCodigoIntermedio.GeneradorCod;
import GeneracionCodigoIntermedio.Terceto;

import java.util.ArrayList;
import java.util.HashMap;


public class GeneradorAssembler {
    public static StringBuilder codAssembler;
    public static StringBuilder segData;
    public static StringBuilder segCode;
    public static ArrayList<Terceto> codIntermedio;


    public GeneradorAssembler(ArrayList<Terceto> codIntermedio) {
        this.codAssembler.append(".586\n" +
                ".model flat, stdcall\n" +
                "\n" +
                "option casemap :none\n" +
                "include \\masm32\\include\\windows.inc\n" +
                "include \\masm32\\include\\kernel32.inc\n" +
                "include \\masm32\\include\\user32.inc\n" +
                "includelib \\masm32\\lib\\kernel32.lib\n" +
                "includelib \\masm32\\lib\\user32.lib\n" );
        this.segData.append(
                ".data\n" +
                "    error_div_cero db \"ERROR EN EJECUCION. NO SE PUEDE DIVIDIR POR CERO\", 0\n" +
                "    error_overf_float db \"ERROR EN EJECUCION. OVERFLOW EN SUMA DE PUNTO FLOTANTE\", 0\n" +
                "    error_overf_int db \"ERROR EN EJECUCION. OVERFLOW EN PRODUCTO DE ENTEROS\", 0\n" );
        declararVariables();
        this.segCode.append(".code\n");
        this.codIntermedio = codIntermedio;
    }

    public static StringBuilder generarAssembler(){
        for (Terceto t: codIntermedio){
            generarCodigo(t);
        }
        return codAssembler;
    }

    private static void generarCodigo(Terceto t){
        switch (t.getOperador()){
            case "+": Plantilla.generarSuma(t);
            case "-": Plantilla.generarResta(t);
            case "/": Plantilla.generarDiv(t);
            case "*": Plantilla.generarMul(t);
            case "=": Plantilla.generarAsig(t);
            case ">": Plantilla.generarCmpMayor(t);
            case "<": Plantilla.generarCmpMenor(t);
            case "<=": Plantilla.generarCmpMenorIgual(t);
            case ">=": Plantilla.generarCmpMayorIgual(t);
            case "==": Plantilla.generarCmpIgual(t);
            case "!!": Plantilla.generarCmpDist(t);
            case "CALL": Plantilla.generarCall(t);
            case "PRINT": Plantilla.generarPrint(t);
            case "LABEL": Plantilla.generarLabel(t);
            case "RETURN": Plantilla.generarReturn(t);
            case "StoD": Plantilla.generarStoD(t);
            case "UtoD": Plantilla.generarUtoD(t);
            case "BF": Plantilla.generarBf(t);
            case "BI": Plantilla.generarBi(t);
        }
    }


    private static void declararVariables(){
        //tiene que recorrer toda la tabla de simbolos buscando variables y, segun el tipo, escribir la inicializacion en el .data
        // SHORT ---> <variable> db ?
        // ULONG ---> <variable> dd ?
        // DOUBLE ---> <variable> dq ?
        HashMap<String, String> variables = Tabla_Simbolos.getVariables();
        for (String lex: variables.keySet()){
            switch (variables.get(lex)){
                case "SHORT": segData.append("  "+lex + " db "+"?"+"\n");
                case "ULONG": segData.append("  "+lex + " dd "+"?"+"\n");
                case "DOUBLE": segData.append("  "+lex + " dq "+"?"+"\n");
            }
        }
    }
}
