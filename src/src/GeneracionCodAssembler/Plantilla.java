package GeneracionCodAssembler;

import GeneracionCodigoIntermedio.Terceto;
//EAX / EBX / ECX / EDX --> 32 bits osea ULONG
//AL / BL / CL / DL --> 8 bits osea para SHORT
//
public class Plantilla {
    private static int contAux = 1;
    public static void generarSuma(Terceto t){
        //MOV R1, <var1>
        //ADD R1, <var2>
        //MOV @aux1, R1
        //Cuestiones:
        // 1--> overflow en sumas de datos de punto flotante
        // CMP OF, 1
        // JE ZF, 1 //REVISAR



    }
    public static void generarResta(Terceto t){

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

    }
    public static void generarUtoD(Terceto t){

    }
    public static void generarBf(Terceto t){

    }
    public static void generarBi(Terceto t){

    }


    private static String getAuxTerceto(String index){
        String i_s = index.substring(1,index.length()-1); //le quito los corchetes a la ref
        int i = Integer.parseInt(i_s); //obtengo la pos del terceto en el arreglo
        return GeneradorAssembler.codIntermedio.get(i).getAuxAsm();
    }
}
