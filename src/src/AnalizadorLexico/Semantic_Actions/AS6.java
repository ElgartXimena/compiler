package AnalizadorLexico.Semantic_Actions;

import AnalizadorLexico.Lexical_Analyzer;
import AnalizadorLexico.Matrix;

//verificar rango constante
// (diferenciar tipos de constantes: corto, largo, largo sin signo, punto flot 64b)
public class AS6 implements Semantic_Action{
    @Override
    public void execute(Lexical_Analyzer la, char simb) {
        String cte = la.getBuffer();
        //obtiene la parte de numero y la convierte a int para chequear rango
        int cteint = Integer.parseInt(cte.substring(0, cte.length()-2));
        double min = Math.pow(-2,7);
        double max = Math.pow(2,7)-1;
        if ((min < cteint)||(cteint>max)){
            System.out.println("Constante fuera de rango");
        }
        //Entero corto = prefijo_s
            //-2^7 --> 2^7 -1
        //Entero largo sin signo = pre_ul
            //0 --> 2^32 -1
        //Punto flot = pref . subf 'd|D' +- num
            //

    }
}
