package AnalizadorLexico;

public class AtributosLexema {
    private int cantUsos;
    private String tipo;
    private String uso;
    public AtributosLexema() {
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

}
