package AnalizadorLexico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public final class Tabla_Palabras_Reservadas {
    private static HashMap<String, Integer> palabras = new HashMap<>(){{
        put("IF",260);
        put("ELSE", 261);
        put("END_IF", 262);
        put("PRINT", 263);
        put("CLASS", 264);
        put("VOID", 265);
        put("SHORT", 266);
        put("ULONG", 267);
        put("DOUBLE", 268);
        put("FOR", 269);
        put("IN", 270);
        put("RANGE", 271);
        put("IMPL", 272);
        put("INTERFACE", 273);
        put("IMPLEMENT", 274);
        put("RETURN", 275);
    }};

    public static boolean existePR(String pr){
        return palabras.containsKey(pr);
    }
    public static int getTokenPR(String pr){return palabras.get(pr);}
}
