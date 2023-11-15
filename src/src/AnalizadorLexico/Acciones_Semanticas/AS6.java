package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
import AnalizadorLexico.Formateador;

//Verifica rango de constantes
public class AS6 implements Accion_Semantica {
    @Override
    public void ejecutar(String simb) {
        String cte = Analizador_Lexico.buffer;
        if (cte.contains("_s")){
            int cteint = Integer.parseInt(cte.substring(0, cte.length()-2));
            double max = Math.pow(2,7);
            if (cteint > max){
                Analizador_Lexico.setError(true);
                System.out.println("ERROR LEXICO. Linea: " + Analizador_Lexico.cantLineas + " Constante fuera de rango");
            } else {
                Analizador_Lexico.setTipoCte("SHORT");
            }

        } else if (cte.contains("_ul")) {
            long cteint_largo = Long.parseLong(cte.substring(0, cte.length()-3));
            //long cteint_largo = Integer.parseInt(cte.substring(0, cte.length()-3));
            double min = 0;
            double max = Math.pow(2,32)-1;
            if ((cteint_largo < min)||(cteint_largo>max)){
                Analizador_Lexico.setError(true);
                System.out.println("ERROR LEXICO. Linea: " + Analizador_Lexico.cantLineas + " Constante fuera de rango");
            }   else {
                Analizador_Lexico.setTipoCte("ULONG");
            }
        } else {
            double max_pos = 1.7976931348623157E308;
            double min_pos = 2.2250738585072014E-308;

            double num = 0;
            cte = cte.toUpperCase();
            if (cte.contains("D")){
                cte = cte.replace("D","E");
            }
            num = Double.parseDouble(cte);
            System.out.println(num);
            if (!((min_pos <= num && num <= max_pos) || num == 0.0)){
                Analizador_Lexico.setError(true);
                System.out.println("ERROR LEXICO. Linea: " + Analizador_Lexico.cantLineas + " Constante fuera de rango");
            }   else {
                Analizador_Lexico.setTipoCte("DOUBLE");
            }
        }
    }
}
