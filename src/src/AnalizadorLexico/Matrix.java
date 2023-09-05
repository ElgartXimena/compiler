package AnalizadorLexico;

public class Matrix {
    protected Object[][] matrix;
    public Object getCell(int eActual, char sActual){
        return matrix[eActual][Simbolo.getColumna(sActual)];
    }
}
