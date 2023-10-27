package AnalizadorLexico;

public class AtributosLexema {
    private int cantUsos;
    private String tipo;
    private String uso;
    private String implementa;
    private String ambito="";
    public AtributosLexema() {
        this.cantUsos = 0;
    }

    public String getImplementa() {
        return implementa;
    }

    public void setImplementa(String implementa) {
        this.implementa = implementa;
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
    public boolean isTipo(String tipo){
        return this.tipo.equals(tipo);
    }

    public boolean isUso(String uso){
        return this.uso.equals(uso);
    }

    public String getAmbito() {
        return ambito;
    }

    public void setAmbito(String amb) {
        this.ambito = ambito.concat("#"+amb);
    }
}
