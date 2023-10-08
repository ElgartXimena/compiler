package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.Analizador_Lexico;
//Verifica rango de constantes
public class AS6 implements Accion_Semantica {
    @Override
    public void ejecutar(Analizador_Lexico la, String simb) {
        String cte = la.getBuffer();
        if (cte.contains("_s")){
            System.out.println("Se reconocio una CONSTANTE de tipo ENTERO CORTO");
            int cteint = Integer.parseInt(cte.substring(0, cte.length()-2));
            double max = Math.pow(2,7);
            if (cteint > max){
                la.error(true);
            }
        } else if (cte.contains("_ul")) {
            System.out.println("Se reconocio una CONSTANTE de tipo ENTERO LARGO SIN SIGNO");
            long cteint_largo = Integer.parseInt(cte.substring(0, cte.length()-3));
            double min = 0;
            double max = Math.pow(2,32)-1;
            if ((cteint_largo < min)||(cteint_largo>max)){
                la.error(true);
            }
        } else {
            System.out.println("Se reconocio una CONSTANTE de tipo PUNTO FLOTANTE");
            double max_pos = Math.pow(1.7976931348623157, 308);
            double min_pos = Math.pow(2.2250738585072014, -308);

            double num = 0;
            cte = cte.toUpperCase();
            if (cte.contains("D")){
                cte = cte.replace("D","E");
            }
            num = Double.parseDouble(cte);
            if (!((min_pos < num && num < max_pos) || num == 0.0)){
                la.error(true);
            }
        }
    }
}