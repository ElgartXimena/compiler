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
    private static StringBuilder code = GeneradorAssembler.segCode;
    private static ArrayList<String> operandos;
    private static String operando1, operando2;
    private static String regOp;
    private static String aux;
    private static int contAux = 1;
    private static int contMsj = 1;
    private static int contCtes = 1;
    public static boolean inFuncion = false; //cuando debemos usar indexacion en arreglo de funciones, esto se pone true
    public static String nombreFun = ""; //para saber en cual array de funcion del map hay que buscar el aux
    private static String jumpBf = ""; //se usa para saber si despues de una comparacion hay que hacer JLE, JL, JG etc

    private static void init(Terceto t){
        //uno o ambos operandos pueden ser ref a tercetos
        operandos = getOperandos(t);
        operando1 = operandos.get(0); //operando 1, sea el lexema o el aux
        operando2 = operandos.get(1);//operando 2, sea el lexema o el aux
        //Dependiendo del tipo, el R1 sera EAX, AL, o ST
        regOp = getTipoRegistro(t);
    }
    private static String getAuxTerceto(String index){
        String i_s = index.substring(1,index.length()-1); //le quito los corchetes a la ref
        int i = Integer.parseInt(i_s); //obtengo la pos del terceto en el arreglo
        String aux;
        if (!inFuncion){
            aux = GeneradorAssembler.codIntermedio.get(i).getAuxAsm();
        } else {
            aux = GeneradorAssembler.codIntermedioFun.get(nombreFun).get(i).getAuxAsm();
        }
        return aux;
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
        if (!operando1.equals("-")) {
            if (operando1.contains("[")) {
                operando1 = getAuxTerceto(operando1);
            } else {
                if (Tabla_Simbolos.getAtributos(operando1).isUso("CONSTANTE")) {
                    operando1 = getCte(operando1);
                }
            }
        }
        if (!operando2.equals("-")){
            if (operando2.contains("[")){
                operando2 = getAuxTerceto(operando2);
            } else {
                if (Tabla_Simbolos.getAtributos(operando2).isUso("CONSTANTE")){
                    operando2 = getCte(operando2);
                }
            }
        }

        ArrayList<String> out = new ArrayList<>();
        out.add(operando1);
        out.add(operando2);
        return out;
    }

    private static void setTipoJumpBf(Terceto t){
        String tipo = t.getTipo();
        String operador = t.getOperador();
        if (tipo.equals("ULONG")) {
            switch (operador) {
                case ">":
                    jumpBf = "JBE";
                    break;
                case ">=":
                    jumpBf = "JB";
                    break;
                case "<":
                    jumpBf = "JAE";
                    break;
                case "<=":
                    jumpBf = "JA";
                    break;
                case "==":
                    jumpBf = "JNE";
                    break;
                case "!!":
                    jumpBf = "JE";
                    break;
            }
        } else {
            switch (operador) {
                case ">":
                    jumpBf = "JLE";
                    break;
                case ">=":
                    jumpBf = "JL";
                    break;
                case "<":
                    jumpBf = "JGE";
                    break;
                case "<=":
                    jumpBf = "JG";
                    break;
                case "==":
                    jumpBf = "JNE";
                    break;
                case "!!":
                    jumpBf = "JE";
                    break;
            }
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
                //agregar al data
                //devolver el nombre en data
            } else if (cte.charAt(cte.length()-1) == '.') { //caso 1.
                cte = cte+"0";
            }
            if (cte.contains("D")){
                cte = cte.replace("D","E");
            }
            String nombreCte = "cte_"+contCtes;
            contCtes++;
            GeneradorAssembler.segData.append("    "+nombreCte + " dq "+cte+"\n");
            return nombreCte;
        }
    }
    public static void generarSuma(Terceto t){
        //MOV R1, <var1>
        //ADD R1, <var2>
        //MOV @aux1, R1
        //Cuestiones:
        // 1--> overflow en sumas de datos de punto flotante
        //setear el aux en el terceto, agregarlo al segData
        aux = getNombreAux(t);
        t.setAuxAsm(aux); //seteamos el aux en el terceto para guardar el resultado de la op
        init(t);
        if (regOp.equals("ST")){
            code.append("    "+"FLD "+ operando1+"\n");
            code.append("    "+"FLD "+ operando2+"\n");
            code.append("    "+"FADD"+"\n");
            code.append("    "+"FCOM max_pos\n");
            code.append("    "+"FSTSW flagsFcom\n");
            code.append("    "+"MOV AX, flagsFcom\n");
            code.append("    "+"SAHF\n");
            code.append("    "+"JA _ovfFloat"+"\n");
            code.append("    "+"FST "+aux+"\n"); //guarda el resultado
        } else {
            code.append("    "+"MOV "+regOp+", "+operando1+"\n");
            code.append("    "+"ADD "+regOp+", "+operando2+"\n");
            code.append("    "+"MOV "+aux+", "+regOp+"\n");
        }
    }
    public static void generarResta(Terceto t){
        //setear el aux en el terceto, agregarlo al segData
        aux = getNombreAux(t);
        t.setAuxAsm(aux); //seteamos el aux en el terceto para guardar el resultado de la op
        init(t);
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
        //setear el aux en el terceto, agregarlo al segData
        aux = getNombreAux(t);
        t.setAuxAsm(aux); //seteamos el aux en el terceto para guardar el resultado de la op
        init(t);
        // 4--> IDIV para short, DIV para ulong, FDIV para double
        if (regOp.equals("ST")){

            code.append("    FLD "+ operando1+"\n");
            code.append("    FLD "+ operando2+"\n");
            code.append("    FCOM double0\n"); //Compara ST y mem.
            code.append("    FSTSW AX\n"); //almacena el estado de la comparación en AX
            code.append("    SAHF\n"); //copia los bits de estado de AX a los flags
            code.append("    JE _divZero \n"); //salta si son iguales
            code.append("    FDIV \n");
            code.append("    FST "+aux+"\n"); //guarda el resultado
        } else if(regOp.equals("EAX")) {
            code.append("    MOV EBX, "+operando2+"\n");
            code.append("    CMP EBX, ulong0\n");
            code.append("    JE _divZero \n");
            code.append("    MOV "+regOp+", "+operando1+"\n");
            code.append("    CDQ\n"); //Extiende a EDX:EAX para la division
            code.append("    DIV EBX\n");
            code.append("    MOV "+aux+", "+regOp+"\n");
        } else {
            code.append("    MOV BL, "+operando2+"\n");
            code.append("    CMP BL, short0\n");
            code.append("    JE _divZero \n");
            code.append("    MOV "+regOp+", "+operando1+"\n"); //operando en AL
            code.append("    CBW\n"); //Extiende a AX para la division
            code.append("    IDIV BL\n"); //Cociente en AL, Resto en AH
            code.append("    MOV "+aux+", "+regOp+"\n"); //pone el cociente en memoria
        }
    }
    public static void generarMul(Terceto t){
        //MOV R1, <var1>
        //MUL R1, <var2>
        //MOV @aux1, R1
        //setear el aux en el terceto, agregarlo al segData
        aux = getNombreAux(t);
        t.setAuxAsm(aux); //seteamos el aux en el terceto para guardar el resultado de la op
        init(t);
        // 4--> IMUL para short, MUL para ulong, FMUL para double
        if (regOp.equals("ST")){
            code.append("    "+"FLD "+ operando1+"\n");
            code.append("    "+"FLD "+ operando2+"\n");
            code.append("    "+"FMUL"+"\n");
            code.append("    "+"FST "+aux+"\n"); //guarda el resultado
        } else if(regOp.equals("EAX")) {
            // 3--> En caso de mul entre enteros, chequear flag de overflow
            code.append("    MOV EBX, "+operando2+"\n");
            code.append("    "+"MOV "+regOp+", "+operando1+"\n");
            code.append("    "+"MUL EBX\n");
            code.append("    "+"JO _ovfInt"+"\n");
            code.append("    "+"MOV "+aux+", "+regOp+"\n");
        } else {
            code.append("    MOV BL, "+operando2+"\n");
            code.append("    "+"MOV "+regOp+", "+operando1+"\n");
            code.append("    "+"IMUL BL\n");
            code.append("    "+"JO _ovfInt"+"\n");
            code.append("    "+"MOV "+aux+", "+regOp+"\n");
        }

    }
    public static void generarAsig(Terceto t){
        init(t);    //CREA VARIABLES AUX EN ASIGNACIONES Y NO SE USAN NUNCA
        if (regOp.equals("ST")){
            code.append("    "+"FLD "+operando2+"\n"); //ponemos en pila ST el op a asignar
            code.append("    "+"FST " + operando1+"\n"); //copiamos en el operando1 lo que hay en tope de pila ST
        } else {

            code.append("    "+"MOV "+regOp +", "+operando2+"\n");
            code.append("    "+"MOV "+operando1 +", "+regOp+"\n");
        }

    }
    public static void generarCmp(Terceto t){
        //setear el aux en el terceto, agregarlo al segData
        aux = getNombreAux(t);
        t.setAuxAsm(aux); //seteamos el aux en el terceto para guardar el resultado de la op
        init(t);
        setTipoJumpBf(t); //setea el tipo de salto para generar luego la BF
        if (regOp.equals("ST")){
            code.append("    FLD "+ operando2+"\n");
            code.append("    FLD "+ operando1+"\n");
            code.append("    FCOM\n"); //Compara ST y ST(1).
            code.append("    FSTSW AX\n"); //almacena el estado de la comparación en AX
            code.append("    SAHF\n"); //copia los bits de estado de AX a los flags
        } else if(regOp.equals("EAX")) {
            code.append("    MOV "+regOp+", "+operando1+"\n");
            code.append("    MOV EBX, "+operando2+"\n");
            code.append("    CMP "+regOp+", EBX\n");
        } else {
            code.append("    MOV "+regOp+", "+operando1+"\n"); //operando en AL
            code.append("    MOV BL, "+operando2+"\n");
            code.append("    CMP "+regOp+", BL\n");
        }
    }
    public static void generarCall(Terceto t){
        GeneradorAssembler.segCode.append("    CALL _"+t.getOperando_1()+"\n");
    }
    public static void generarPrint(Terceto t){
        String idmsj = "msj_"+String.valueOf(contMsj);
        contMsj++;
        GeneradorAssembler.segData.append("    " + idmsj + " db " + "\"" + t.getOperando_1() + "\", 0" +"\n");
        GeneradorAssembler.segCode.append("    invoke StdOut, addr "+idmsj+"\n");
    }
    public static void generarLabel(Terceto t){
        String lb = t.getOperador() + t.getOperando_1()+":";
        GeneradorAssembler.segCode.append(lb+"\n");
    }
    public static void generarReturn(Terceto t){
        GeneradorAssembler.segCode.append("    RET"+"\n");
    }
    public static void generarStoD(Terceto t){
        //setear el aux en el terceto, agregarlo al segData
        aux = getNombreAux(t);
        t.setAuxAsm(aux); //seteamos el aux en el terceto para guardar el resultado de la op
        init(t);
        code.append("    MOV AL, "+operando1+"\n"); //cargamos el operando en registro
        code.append("    CBW"+"\n"); //convierte de 8 (desde AL) a 16 poniendo el resultado en AX
        code.append("    MOV aux16, AX"+"\n"); //cargamos en mem el reg AX para usarlo en FILD que solo soporta MEM
        code.append("    FILD aux16"+"\n"); //Pone en ST el operando de memoria de 2, 4 u 8 bytes, que se interpreta
                                            //como un número entero y se convierte al formato real temporal
        code.append("    FST "+aux+"\n"); //Sacamos de la pila el operando ya convertido y lo ponemos en aux
                                        //que es el correspondiente al Terceto de conversion
    }
    public static void generarUtoD(Terceto t){
        //setear el aux en el terceto, agregarlo al segData
        aux = getNombreAux(t);
        t.setAuxAsm(aux); //seteamos el aux en el terceto para guardar el resultado de la op
        init(t);
        code.append("    MOV EAX, "+operando1+"\n");
        code.append("    MOV aux32, EAX\n");
        code.append("    FILD aux32\n"); //Pone en ST el operando de memoria de 2, 4 u 8 bytes, que se interpreta
        //como un número entero y se convierte al formato real temporal
        code.append("    FST "+aux+"\n"); //Sacamos de la pila el operando ya convertido y lo ponemos en aux
        //que es el correspondiente al Terceto de conversion
    }
    public static void generarBf(Terceto t){
        String ref = t.getOperando_1();
        String label = " LABEL"+nombreFun+ref.substring(1, ref.length()-1);
        GeneradorAssembler.segCode.append("    "+jumpBf+label+"\n");
    }

    public static void generarBi(Terceto t){
        String ref = t.getOperando_1();
        String label = "LABEL"+nombreFun+ref.substring(1, ref.length()-1);
        GeneradorAssembler.segCode.append("    JMP "+label+"\n");
    }

}
