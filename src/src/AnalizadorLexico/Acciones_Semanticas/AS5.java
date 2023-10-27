package AnalizadorLexico.Acciones_Semanticas;

import AnalizadorLexico.*;
//Busca en la tabla de simbolos, si no esta da de alta. Si esta, setea el token
public class AS5 implements Accion_Semantica {
    @Override
    public void ejecutar(String simb) {
        Analizador_Lexico.setToken(Identificador.getToken());
        if (!Tabla_Simbolos.existeSimbolo(Analizador_Lexico.buffer)) {//buscar en la tabla de simbolos
            Tabla_Simbolos.insertarSimbolo(Analizador_Lexico.buffer, new AtributosLexema());
            if (Identificador.getToken()==258){
                //Si se reconocio una CTE en rango, se debe agregar el tipo a la tabla de simbolos
                Tabla_Simbolos.getAtributos(Analizador_Lexico.buffer).setTipo(Analizador_Lexico.getTipoCte());
            }
        }
    }
}
