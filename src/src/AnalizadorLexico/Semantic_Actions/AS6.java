package AnalizadorLexico.Semantic_Actions;

import AnalizadorLexico.Lexical_Analyzer;
import AnalizadorLexico.Matrix;

//verificar rango constante
// (diferenciar tipos de constantes: corto, largo, largo sin signo, punto flot 64b)
//Entero corto = prefijo_s
//-2^7 --> 2^7 -1
//Entero largo sin signo = pre_ul
//0 --> 2^32 -1
public class AS6 implements Semantic_Action{
    @Override
    public void execute(Lexical_Analyzer la, char simb) {
        String cte = la.getBuffer();
        if (cte.contains("_s")){
            int cteint = Integer.parseInt(cte.substring(0, cte.length()-2));
            double min = Math.pow(-2,7);
            double max = Math.pow(2,7)-1;
            if ((cteint < min)||(cteint>max)){
                System.out.println("Constante fuera de rango");
            }
        } else if (cte.contains("_ul")) {
            long cteint_largo = Integer.parseInt(cte.substring(0, cte.length()-3));
            double min = 0;
            double max = Math.pow(2,32)-1;
            if ((cteint_largo < min)||(cteint_largo>max)){
                System.out.println("Constante fuera de rango");
            }
        } else {
            double max_pos = Math.pow(1.7976931348623157, 308);
            double min_pos = Math.pow(2.2250738585072014, -308);
            double max_neg = -Math.pow(2.2250738585072014, -308);
            double min_neg = -Math.pow(1.7976931348623157, 308);

            double num = 0;
            cte = cte.toUpperCase();
            if (cte.contains("D")){
                cte = cte.replace("D","E");
            }
            num = Double.parseDouble(cte);
            if (!((min_pos < num && num < max_pos) || (min_neg < num && num < max_neg) || num == 0.0)){
                System.out.println("Constante fuera de rango");
            }
        }
    }
}
