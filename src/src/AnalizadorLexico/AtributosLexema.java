package AnalizadorLexico;

public class AtributosLexema {
    private String tipo, uso;

    public AtributosLexema() {

    }

    public AtributosLexema(String tipo, String uso) {
        this.tipo = tipo;
        this.uso = uso;
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
}
