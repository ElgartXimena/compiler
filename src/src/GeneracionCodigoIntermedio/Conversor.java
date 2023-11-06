package GeneracionCodigoIntermedio;

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
    public static Terceto convertir(String operador, String operando1, String operando2){
        //Chequear en la matriz de conversiones si es necesario realizar alguna conversion.
        //en caso de ser necesario, utilizar los metodos privados de esta clase
        //en caso de incompatibilidad de tipos, arrojar error.
        Terceto t = new Terceto("stod",operando1, "", "DOUBLE");
        return null;
    }

    public static String getTipo(String tipo1, String tipo2, String operacion){
        int fila = getIndex(tipo1);
        int col = getIndex(tipo2);
        String elemento = "";
        if (operacion.equals("a")){
            //matriz de asignaciones
            elemento = mAsig[fila][col];
            return elemento.substring(1,elemento.length());
        } else {
            //matriz de operaciones
            elemento = mOps[fila][col];
            return elemento.substring(1,elemento.length());
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

}
