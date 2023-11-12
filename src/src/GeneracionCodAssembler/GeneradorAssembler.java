package GeneracionCodAssembler;

import AnalizadorLexico.Tabla_Simbolos;
import GeneracionCodigoIntermedio.GeneradorCod;
import GeneracionCodigoIntermedio.Terceto;

import java.util.ArrayList;
import java.util.HashMap;


public class GeneradorAssembler {
    public static StringBuilder codAssembler = new StringBuilder();
    public static StringBuilder segData = new StringBuilder();
    public static StringBuilder segCode = new StringBuilder();
    public static ArrayList<Terceto> codIntermedio;
    public static HashMap<String, ArrayList<Terceto>> codIntermedioFun;

    public GeneradorAssembler() {
        codIntermedio = GeneradorCod.getTercetos();
        codIntermedioFun = GeneradorCod.getTercetosFuncion();
        this.codAssembler.append(".386\n" +
                ".model flat, stdcall\n" +
                "\n" +
                "option casemap :none\n" +
                "include \\masm32\\include\\windows.inc\n" +
                "include \\masm32\\include\\kernel32.inc\n" +
                "include \\masm32\\include\\user32.inc\n" +
                "include \\masm32\\include\\masm32.inc \n" +
                "includelib \\masm32\\lib\\kernel32.lib\n" +
                "includelib \\masm32\\lib\\user32.lib\n" +
                "includelib \\masm32\\lib\\masm32.lib");
        this.segData.append(
                ".data\n" +
                "    error_div_cero db \"ERROR EN EJECUCION. NO SE PUEDE DIVIDIR POR CERO\", 0\n" +
                "    error_overf_float db \"ERROR EN EJECUCION. OVERFLOW EN SUMA DE PUNTO FLOTANTE\", 0\n" +
                "    error_overf_int db \"ERROR EN EJECUCION. OVERFLOW EN PRODUCTO DE ENTEROS\", 0\n" +
                "    error_asig_neg db \"ERROR EN EJECUCION. NO SE PUEDE ASIGNAR UN NUMERO NEGATIVO A ULONG\", 0\n" +
                "    aux16 dw ?"+"\n");
        declararVariables();
        this.segCode.append(".code\n"+"start:"+"\n");
    }

    public StringBuilder generarAssembler(){
        for (Terceto t: codIntermedio){
            generarCodigo(t);
        }
        for (String key: codIntermedioFun.keySet()){
            segCode.append("_"+key+": "+"\n"); //pone el label de la funcion
            for (Terceto t: codIntermedioFun.get(key)){
                generarCodigo(t);
            }
        }
        for (String variable: Tabla_Simbolos.getVariables().keySet()){
            System.out.println("Variable: "+variable);
            segCode.append("    invoke StdOut, addr " + variable +"\n");
        }
        segCode.append("_quit:      invoke ExitProcess, 0"+"\n");
        segCode.append("_divZero:   invoke StdOut, addr error_div_cero"+"\n");
        segCode.append("            JMP _quit"+"\n");
        segCode.append("_ovfFloat:  invoke StdOut, addr error_overf_float"+"\n");
        segCode.append("            JMP _quit"+"\n");
        segCode.append("_ovfInt:    invoke StdOut, addr error_overf_int"+"\n");
        segCode.append("            JMP _quit"+"\n");
        segCode.append("_asigNeg:   invoke StdOut, addr error_asig_neg"+"\n");
        segCode.append("            JMP _quit"+"\n");
        segCode.append("end start");
        codAssembler.append(segData);
        codAssembler.append(segCode);
        return codAssembler;
    }

    private static void generarCodigo(Terceto t){
        switch (t.getOperador()){
            case "+":
                Plantilla.generarSuma(t);
                break;
            case "-":
                Plantilla.generarResta(t);
                break;
            case "/":
                Plantilla.generarDiv(t);
                break;
            case "*":
                Plantilla.generarMul(t);
                break;
            case "=":
                Plantilla.generarAsig(t);
                break;
            case ">":
            case "<":
            case "<=":
            case ">=":
            case "==":
            case "!!":
                Plantilla.generarCmp(t);
                break;
            case "CALL":
                Plantilla.generarCall(t);
                break;
            case "PRINT":
                Plantilla.generarPrint(t);
                break;
            case "LABEL":
                Plantilla.generarLabel(t);
                break;
            case "RETURN":
                Plantilla.generarReturn(t);
                break;
            case "StoD":
                Plantilla.generarStoD(t);
                break;
            case "UtoD":
                Plantilla.generarUtoD(t);
                break;
            case "BF":
                Plantilla.generarBf(t);
                break;
            case "BI":
                Plantilla.generarBi(t);
                break;
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
                case "SHORT":
                    segData.append("    "+lex + " db "+"?"+"\n");
                    break;
                case "ULONG":
                    segData.append("    "+lex + " dd "+"?"+"\n");
                    break;
                case "DOUBLE":
                    segData.append("    "+lex + " dq "+"?"+"\n");
                    break;
            }
        }
    }
}
