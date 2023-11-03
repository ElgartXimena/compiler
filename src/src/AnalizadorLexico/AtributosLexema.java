package AnalizadorLexico;

public class AtributosLexema {
    private int cantUsos;
    private String tipo;
    private String uso;
    private String implementa;
    private String hereda;
    private boolean isImplementado;
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
        hereda = "";
        isImplementado = false;
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

    public String getHereda() {
        return hereda;
    }

    public void setHereda(String hereda) {
        this.hereda = hereda;
    }

    public boolean isImplementado() {
        return isImplementado;
    }

    public void setImplementado(boolean implementado) {
        isImplementado = implementado;
    }

    public boolean coincideTipoParametro(String tipoP){
        return tipoP.equals(tipoParametro);
    }

}
