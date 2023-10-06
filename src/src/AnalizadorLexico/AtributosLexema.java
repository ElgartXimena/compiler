package AnalizadorLexico;

public class AtributosLexema {
    private String tipo, uso;
    private int cantUsos;
    public AtributosLexema() {

    }

    public AtributosLexema(String tipo, String uso) {
        this.tipo = tipo;
        this.uso = uso;
        this.cantUsos = 0;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }
    public void sumarUso(){
        cantUsos++;
    }
    public boolean isCero(){
        return cantUsos==0;
    }

    @Override
    public String toString() {
        return String.format("| %-10s | %-15s | %-8d |", tipo, uso, cantUsos);
    }
}
