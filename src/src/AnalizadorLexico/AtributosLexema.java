package AnalizadorLexico;

public class AtributosLexema {
    private String tipo;
    private int cantUsos;
    public AtributosLexema() {

    }

    public AtributosLexema(String tipo) {
        this.tipo = tipo;
        this.cantUsos = 0;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void sumarUso(){
        cantUsos++;
    }
    public boolean isCero(){
        return cantUsos==0;
    }
    public boolean isTipo(String tipo){
        return this.tipo.equals(tipo);
    }
    @Override
    public String toString() {
        return String.format("| %-10s | %-8d |", tipo, cantUsos);
    }
}
