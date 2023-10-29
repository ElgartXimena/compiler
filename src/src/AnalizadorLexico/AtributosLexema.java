package AnalizadorLexico;

public class AtributosLexema {
    private int cantUsos;
    private String tipo;
    private String uso;
    private String implementa;
    private String ambito;
    private String parametro;
    private String tipoParametro;
    public AtributosLexema() {
        tipo ="";
        uso ="";
        implementa ="";
        ambito ="";
        parametro ="";
        tipoParametro ="";
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
    public boolean tieneParametro(){
        if (parametro.equals("")){
            return false;
        }
        return true;
    }
    public boolean coincideTipoParametro(String tipoP){
        return tipoP.equals(tipoParametro);
    }

}
