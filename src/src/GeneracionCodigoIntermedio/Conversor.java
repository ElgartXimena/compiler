package GeneracionCodigoIntermedio;

import AnalizadorLexico.Tabla_Simbolos;

public class Conversor {
    private static String[][] mAsig = new String[][]
            {
                    {"-SHORT", "-error","-error"},
                    {"-error","-ULONG","-error"},
                    {"2DOUBLE","2DOUBLE","-DOUBLE"},
            };
    private static String[][] mOps = new String[][]
            {
                    {"-SHORT", "-error","1DOUBLE"},
                    {"-error","-ULONG","1DOUBLE"},
                    {"2DOUBLE","2DOUBLE","-DOUBLE"},
            };
    public static String operandoConvertido = "";
    public static String getTipo(String tipo1, String tipo2, String operacion){
        if (tipo1.equals("error")||tipo2.equals("error")){
            return "error";
        } else {
            int fila = getIndex(tipo1);
            int col = getIndex(tipo2);
            String elemento = "";
            if (operacion.equals("a")){
                //matriz de asignaciones
                elemento = mAsig[fila][col];
            } else {
                //matriz de operaciones
                elemento = mOps[fila][col];
            }
            return elemento.substring(1);
        }
    }

    private static int getIndex(String tipo){
        switch (tipo){
            case "SHORT": return 0;
            case "ULONG": return 1;
            case "DOUBLE": return 2;
            default: return -1;
        }
    }

    public static Terceto getTercetoConversion(String operacion, String lexema1, String lexema2){
        String tipo1, tipo2;
        if (lexema1.contains("[")){
            tipo1 = GeneradorCod.getTipoTerceto(GeneradorCod.getIndexActual());
        } else {
            tipo1 = Tabla_Simbolos.getAtributos(lexema1).getTipo();
        }
        if (lexema2.contains("[")){
            tipo2 = GeneradorCod.getTipoTerceto(GeneradorCod.getIndexActual());
        } else{
            System.out.println("LEXEMA 2 "+lexema2);
            tipo2 = Tabla_Simbolos.getAtributos(lexema2).getTipo();
        }
        int fila = getIndex(tipo1);
        int col = getIndex(tipo2);
        String elemento = "";
        if (operacion.equals("a")){
            //matriz de asignaciones
            elemento = mAsig[fila][col]; //"2DOUBLE"
        } else {
            //matriz de operaciones
            elemento = mOps[fila][col];
        }
        return generarTerceto(tipo1, lexema1, tipo2, lexema2, elemento);
    }
    private static Terceto generarTerceto(String tipo1, String lexema1, String tipo2, String lexema2, String elemento){
        String operandoConvertir = elemento.substring(0,1); //--> convertir op1, op2 o ninguno
        operandoConvertido = operandoConvertir;
        String tipoResultante = elemento.substring(1); //--> a que convierte
        if (operandoConvertir.equals("1")){
            //generar terceto con lexema1 y tipo resultante. tipo1[0] to tipoResultante[0]
            return crearTercetoConversion(tipo1, lexema1, tipoResultante);
        } else if (operandoConvertir.equals("2")){
            return crearTercetoConversion(tipo2, lexema2, tipoResultante);
        } else {
            return null;
        }
    }
    private static Terceto crearTercetoConversion(String tipo, String lexema, String tipoResult){
        String conversion = tipo.substring(0,1) +"to"+ tipoResult.substring(0,1);
        Terceto t = new Terceto(conversion, lexema);
        t.setTipo(tipoResult);
        return t;
    }

}
