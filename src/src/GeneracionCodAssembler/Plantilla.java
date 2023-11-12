package GeneracionCodAssembler;

import AnalizadorLexico.Analizador_Lexico;
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
    private static int contMsj = 1;
    private static String jumpBf = ""; //se usa para saber si despues de una comparacion hay que hacer JLE, JL, JG etc
    private static String getAuxTerceto(String index){
        String i_s = index.substring(1,index.length()-1); //le quito los corchetes a la ref
        int i = Integer.parseInt(i_s); //obtengo la pos del terceto en el arreglo
        return GeneradorAssembler.codIntermedio.get(i).getAuxAsm();
    }
    private static String getNombreAux(Terceto t){
        String tipo = t.getTipo();
        String aux = "@aux"+String.valueOf(contAux);
        switch (tipo){
            case "SHORT":
                GeneradorAssembler.segData.append("    "+aux + " db "+"?"+"\n");
                break;
            case "ULONG":
                GeneradorAssembler.segData.append("    "+aux + " dd "+"?"+"\n");
                break;
            case "DOUBLE":
                GeneradorAssembler.segData.append("    "+aux + " dq "+"?"+"\n");
                break;
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
        } else {
            if (Tabla_Simbolos.getAtributos(operando1).isUso("CONSTANTE")){
                operando1 = getCte(operando1);
            }
        }
        if (operando2.contains("[")){
            operando2 = getAuxTerceto(operando2);
        } else {
            if (Tabla_Simbolos.getAtributos(operando2).isUso("CONSTANTE")){
                operando2 = getCte(operando2);
            }
        }
        ArrayList<String> out = new ArrayList<>();
        out.add(operando1);
        out.add(operando2);
        return out;
    }

    private static void setTipoJumpBf(String operador){
        switch (operador){
            case ">"    :
                jumpBf = "JLE";
                break;
            case ">="   :
                jumpBf = "JL";
                break;
            case "<"    :
                jumpBf = "JGE";
                break;
            case "<="   :
                jumpBf = "JG";
                break;
            case "=="   :
                jumpBf = "JNE";
                break;
            case "!!"   :
                jumpBf = "JE";
                break;
        }
    }

    private static String getCte(String cteOriginal){
        if (cteOriginal.contains("_s")){
            return cteOriginal.substring(0, cteOriginal.length()-2);
        } else if (cteOriginal.contains("_ul")) {
            return cteOriginal.substring(0, cteOriginal.length()-3);
        } else {
            String cte = cteOriginal.toUpperCase();
            if (cte.charAt(0) == '.'){ //caso .1
                cte = "0"+cte;
            } else if (cte.charAt(cte.length()-1) == '.') { //caso 1.
                cte = cte+"0";
            }
            if (cte.contains("D")){
                cte = cte.replace("D","E");
            }
            return cte;
        }
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
        StringBuilder code = GeneradorAssembler.segCode;
        // 5--> setear el aux en el terceto, agregarlo al segData
        String aux = getNombreAux(t);
        t.setAuxAsm(aux); //seteamos el aux en el terceto para guardar el resultado de la op

        // 1--> uno o ambos operandos pueden ser ref a tercetos
        ArrayList<String> operandos = getOperandos(t);
        String operando1 = operandos.get(0); //operando 1, sea el lexema o el aux
        String operando2 = operandos.get(1);//operando 2, sea el lexema o el aux

        // 2--> Dependiendo del tipo, el R1 sera EAX, AL, o ST
        String regOp = getTipoRegistro(t);
        // 4--> IMUL para short, MUL para ulong, FMUL para double
        code.append("    CMP "+operando2+", 0\n");
        code.append("    JE _divZero \n");
        if (regOp.equals("ST")){
            code.append("    FLD "+ operando1+"\n");
            code.append("    FLD "+ operando2+"\n");
            code.append("    FDIV \n");
            code.append("    FST "+aux+"\n"); //guarda el resultado
        } else if(regOp.equals("EAX")) {
            // 3--> En caso de mul entre enteros, chequear flag de overflow
            code.append("    MOV "+regOp+", "+operando1+"\n");
            code.append("    DIV "+regOp+", "+operando2+"\n");
            code.append("    MOV "+aux+", "+regOp+"\n");
        } else {
            code.append("    MOV "+regOp+", "+operando1+"\n");
            code.append("    IDIV "+regOp+", "+operando2+"\n");
            code.append("    MOV "+aux+", "+regOp+"\n");
        }
    }
    public static void generarMul(Terceto t){
        //MOV R1, <var1>
        //MUL R1, <var2>
        //MOV @aux1, R1
        //cuestiones:
        StringBuilder code = GeneradorAssembler.segCode;
        // 5--> setear el aux en el terceto, agregarlo al segData
        String aux = getNombreAux(t);
        t.setAuxAsm(aux); //seteamos el aux en el terceto para guardar el resultado de la op

        // 1--> uno o ambos operandos pueden ser ref a tercetos
        ArrayList<String> operandos = getOperandos(t);
        String operando1 = operandos.get(0); //operando 1, sea el lexema o el aux
        String operando2 = operandos.get(1);//operando 2, sea el lexema o el aux

        // 2--> Dependiendo del tipo, el R1 sera EAX, AL, o ST
        String regOp = getTipoRegistro(t);
        // 4--> IMUL para short, MUL para ulong, FMUL para double
        if (regOp.equals("ST")){
            code.append("    "+"FLD "+ operando1+"\n");
            code.append("    "+"FLD "+ operando2+"\n");
            code.append("    "+"FMUL"+"\n");
            code.append("    "+"FST "+aux+"\n"); //guarda el resultado
        } else if(regOp.equals("EAX")) {
            // 3--> En caso de mul entre enteros, chequear flag de overflow
            code.append("    "+"MOV "+regOp+", "+operando1+"\n");
            code.append("    "+"MUL "+regOp+", "+operando2+"\n");
            code.append("    "+"JO _ovfInt"+"\n");
            code.append("    "+"MOV "+aux+", "+regOp+"\n");
        } else {
            code.append("    "+"MOV "+regOp+", "+operando1+"\n");
            code.append("    "+"IMUL "+regOp+", "+operando2+"\n");
            code.append("    "+"JO _ovfInt"+"\n");
            code.append("    "+"MOV "+aux+", "+regOp+"\n");
        }

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
    public static void generarCmp(Terceto t){
        ArrayList<String> operandos = getOperandos(t);
        String operando1 = operandos.get(0); //operando 1, sea el lexema o el aux
        String operando2 = operandos.get(1);//operando 2, sea el lexema o el aux
        setTipoJumpBf(t.getOperador()); //setea el tipo de salto para generar luego la BF
        GeneradorAssembler.segCode.append("    CMP "+operando1 + ", "+ operando2+"\n");
    }
    public static void generarCall(Terceto t){
        GeneradorAssembler.segCode.append("    CALL _"+t.getOperando_1()+"\n");
    }
    public static void generarPrint(Terceto t){
        String idmsj = "msj_"+String.valueOf(contMsj);
        contMsj++;
        GeneradorAssembler.segData.append("    " + idmsj + " db " + "\"" + t.getOperando_1() + "\"" +"\n");
        GeneradorAssembler.segCode.append("    invoke StdOut, addr "+idmsj+"\n");
    }
    public static void generarLabel(Terceto t){
        GeneradorAssembler.segCode.append("    "+t.getOperador()+"\n");
    }
    public static void generarReturn(Terceto t){
        GeneradorAssembler.segCode.append("    RET"+"\n");
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
        String aux = getNombreAux(t);
        t.setAuxAsm(aux); //seteamos el aux en el terceto para guardar el resultado de la op
        String operando = getOperandos(t).get(0);
        StringBuilder code = GeneradorAssembler.segCode;
        code.append("    FILD "+operando+"\n"); //Pone en ST el operando de memoria de 2, 4 u 8 bytes, que se interpreta
        //como un número entero y se convierte al formato real temporal
        code.append("    FST "+aux+"\n"); //Sacamos de la pila el operando ya convertido y lo ponemos en aux
        //que es el correspondiente al Terceto de conversion
    }
    public static void generarBf(Terceto t){
        String label = " LABEL"+t.getOperando_1();
        GeneradorAssembler.segCode.append("    "+jumpBf+label+"\n");
    }

    public static void generarBi(Terceto t){
        String label = "LABEL"+t.getOperando_1();
        GeneradorAssembler.segCode.append("    JMP "+label+"\n");
    }

}
