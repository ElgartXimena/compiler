package GeneracionCodAssembler;

import AnalizadorLexico.AtributosLexema;
import AnalizadorLexico.Tabla_Simbolos;
import GeneracionCodigoIntermedio.Terceto;

import java.util.ArrayList;
import java.util.List;

//EAX / EBX / ECX / EDX --> 32 bits osea ULONG
//AL / BL / CL / DL --> 8 bits osea para SHORT
//
public class Plantilla {
    private static int contAux = 1;
    private static String getAuxTerceto(String index){
        String i_s = index.substring(1,index.length()-1); //le quito los corchetes a la ref
        int i = Integer.parseInt(i_s); //obtengo la pos del terceto en el arreglo
        return GeneradorAssembler.codIntermedio.get(i).getAuxAsm();
    }
    private static String getNombreAux(Terceto t){
        String tipo = t.getTipo();
        String aux = "@aux"+String.valueOf(contAux);
        switch (tipo){
            case "SHORT": GeneradorAssembler.segData.append("    "+aux + " db "+"?"+"\n");
            case "ULONG": GeneradorAssembler.segData.append("    "+aux + " dd "+"?"+"\n");
            case "DOUBLE": GeneradorAssembler.segData.append("    "+aux + " dq "+"?"+"\n");
        }
        AtributosLexema at = new AtributosLexema();
        at.setTipo(tipo);
        at.setUso("AUXILIAR");
        Tabla_Simbolos.insertarSimbolo(aux, at);
        contAux++;
        return aux;
    }
    private static String getTipoRegistro(Terceto t){
        String tipo = t.getTipo();
        switch (tipo){
            case "SHORT": return "AL";
            case "ULONG": return "EAX";
            case "DOUBLE": return "ST";
            default: return null;
        }
    }

    private static ArrayList<String> getOperandos(Terceto t){
        String operando1 = t.getOperando_1();
        String operando2 = t.getOperando_2();
        if (operando1.contains("[")){
            operando1 = getAuxTerceto(operando1);
        }
        if (operando2.contains("[")){
            operando2 = getAuxTerceto(operando2);
        }
        ArrayList<String> out = new ArrayList<>();
        out.add(operando1);
        out.add(operando2);
        return out;
    }
    public static void generarSuma(Terceto t){
        //MOV R1, <var1>
        //ADD R1, <var2>
        //MOV @aux1, R1
        //Cuestiones:
        // 1--> overflow en sumas de datos de punto flotante
        // CMP OF, 1
        String aux = getNombreAux(t);
        t.setAuxAsm(aux); //seteamos el aux en el terceto para guardar el resultado de la op
        String regOp = getTipoRegistro(t);
        ArrayList<String> operandos = getOperandos(t);
        String operando1 = operandos.get(0); //operando 1, sea el lexema o el aux
        String operando2 = operandos.get(1);//operando 2, sea el lexema o el aux
        StringBuilder code = GeneradorAssembler.segCode;
        if (regOp.equals("ST")){
            code.append("    "+"FLD "+ operando1+"\n");
            code.append("    "+"FLD "+ operando2+"\n");
            code.append("    "+"FADD"+"\n");
            code.append("    "+"JO _ovfFloat"+"\n");
            code.append("    "+"FST "+aux+"\n"); //guarda el resultado
        } else {
            code.append("    "+"MOV "+regOp+", "+operando1+"\n");
            code.append("    "+"ADD "+regOp+", "+operando2+"\n");
            code.append("    "+"MOV "+aux+", "+regOp+"\n");
        }
    }
    public static void generarResta(Terceto t){
        String aux = getNombreAux(t);
        t.setAuxAsm(aux); //seteamos el aux en el terceto para guardar el resultado de la op
        String regOp = getTipoRegistro(t);
        ArrayList<String> operandos = getOperandos(t);
        String operando1 = operandos.get(0); //operando 1, sea el lexema o el aux
        String operando2 = operandos.get(1);//operando 2, sea el lexema o el aux
        StringBuilder code = GeneradorAssembler.segCode;
        if (regOp.equals("ST")){
            code.append("    "+"FLD "+ operando1+"\n");
            code.append("    "+"FLD "+ operando2+"\n");
            code.append("    "+"FSUB"+"\n");
            code.append("    "+"FST "+aux+"\n"); //guarda el resultado
        } else {
            code.append("    "+"MOV "+regOp+", "+operando1+"\n");
            code.append("    "+"SUB "+regOp+", "+operando2+"\n");
            code.append("    "+"MOV "+aux+", "+regOp+"\n");
        }
    }
    public static void generarDiv(Terceto t){

    }
    public static void generarMul(Terceto t){
        //MOV R1, <var1>
        //MUL R1, <var2>
        //MOV @aux1, R1
        //cuestiones:
        // 1--> uno o ambos operandos pueden ser ref a tercetos
        // 2--> Dependiendo del tipo, el R1 sera EAX, AL, o ST
        // 3--> En caso de mul entre enteros, chequear flag de overflow
        // 4--> IMUL para short, MUL para ulong, FMUL para double
        // 5--> setear el aux en el terceto, agregarlo al segData

    }
    public static void generarAsig(Terceto t){
        String regOp = getTipoRegistro(t);
        ArrayList<String> operandos = getOperandos(t);
        String operando1 = operandos.get(0); //operando 1, sea el lexema o el aux
        String operando2 = operandos.get(1);//operando 2, sea el lexema o el aux
        StringBuilder code = GeneradorAssembler.segCode;
        if (regOp.equals("ST")){
            code.append("    "+"FLD "+operando2+"\n"); //ponemos en pila ST el op a asignar
            code.append("    "+"FST " + operando1+"\n"); //copiamos en el operando1 lo que hay en tope de pila ST
        } else {
            if (regOp.equals("EAX")){
                //se debe chequear que no se asigne un negativo a un ULONG
                code.append("    "+"CMP "+operando1+", "+operando2+"\n");
                code.append("    "+"JL _asigNeg"+"\n");
            }
            code.append("    "+"MOV "+regOp +", "+operando2+"\n");
            code.append("    "+"MOV "+operando1 +", "+regOp+"\n");
        }

    }
    public static void generarCmpMayor(Terceto t){

    }
    public static void generarCmpMenor(Terceto t){

    }
    public static void generarCmpMenorIgual(Terceto t){

    }
    public static void generarCmpMayorIgual(Terceto t){

    }
    public static void generarCmpIgual(Terceto t){

    }
    public static void generarCmpDist(Terceto t){

    }
    public static void generarCall(Terceto t){

    }
    public static void generarPrint(Terceto t){

    }
    public static void generarLabel(Terceto t){

    }
    public static void generarReturn(Terceto t){

    }
    public static void generarStoD(Terceto t){
        String aux = getNombreAux(t);
        t.setAuxAsm(aux); //seteamos el aux en el terceto para guardar el resultado de la op
        String operando = getOperandos(t).get(0);
        StringBuilder code = GeneradorAssembler.segCode;
        code.append("    MOV AL, "+operando+"\n"); //cargamos el operando en registro
        code.append("    CBW"+"\n"); //convierte de 8 (desde AL) a 16 poniendo el resultado en AX
        code.append("    MOV aux16, AX"+"\n"); //cargamos en mem el reg AX para usarlo en FILD que solo soporta MEM
        code.append("    FILD aux16"+"\n"); //Pone en ST el operando de memoria de 2, 4 u 8 bytes, que se interpreta
                                            //como un número entero y se convierte al formato real temporal
        code.append("    FST "+aux+"\n"); //Sacamos de la pila el operando ya convertido y lo ponemos en aux
                                        //que es el correspondiente al Terceto de conversion
    }
    public static void generarUtoD(Terceto t){

    }
    public static void generarBf(Terceto t){

    }

    public static void generarBi(Terceto t){

    }

}
