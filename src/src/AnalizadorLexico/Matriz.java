package AnalizadorLexico;

public class Matriz {
    protected Object[][] matrix;
    public Object getCelda(int eActual, String sActual){
        return matrix[eActual][Simbolo.getColumna(eActual, sActual)];
    }
}
