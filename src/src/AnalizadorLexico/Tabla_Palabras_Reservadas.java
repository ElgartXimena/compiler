package AnalizadorLexico;

import java.util.ArrayList;
import java.util.List;

public class Tabla_Palabras_Reservadas {
    private static ArrayList<String> palabras = new ArrayList<>(List.of("IF","ELSE","END_IF","PRINT","CLASS","VOID","SHORT","ULONG",
            "DOUBLE","FOR","IN","RANGE","IMPL","INTERFACE","IMPLEMENT"));;

    public static boolean existePR(String pr){
        return palabras.contains(pr);
    }
}
