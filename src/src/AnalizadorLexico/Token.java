package AnalizadorLexico;

public class Token {
    private int id; //Token en si, el numerito
    private String lexema;
    private int nroLinea;
    //{......otros}


    public Token(int id, String lexema, int nroLinea) {
        this.id = id;
        this.lexema = lexema;
        this.nroLinea = nroLinea;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public int getNroLinea() {
        return nroLinea;
    }

    public void setNroLinea(int nroLinea) {
        this.nroLinea = nroLinea;
    }
}
