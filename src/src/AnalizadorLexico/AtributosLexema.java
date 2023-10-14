package AnalizadorLexico;

public class AtributosLexema {
    private int cantUsos;

    public AtributosLexema() {
        this.cantUsos = 0;
    }


    public void sumarUso(){
        cantUsos++;
    }
    public boolean isCero(){
        return cantUsos==0;
    }

}
