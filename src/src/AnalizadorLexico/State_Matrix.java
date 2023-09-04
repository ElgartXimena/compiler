package AnalizadorLexico;

import java.util.ArrayList;
import java.util.List;

public class State_Matrix {
    private ArrayList<Character> simbolos = new ArrayList(List.of("{","}",";",",","(",")"));
    private int [][] matriz_transicion = new int[19][24];

    public State_Matrix() {

    }

    public int getNextState(int eActual, char sActual){
        int symbolIndex = simbolos.indexOf(sActual);
        return matriz_transicion[eActual][symbolIndex];
    }
}
