package GeneracionCodigoIntermedio;

public class Terceto {
    private String operador = "-";
    private String operando_1 = "-";
    private String operando_2 = "-";
    private String tipo = "-";
    private String auxAsm = "-";
    public Terceto(String operador, String operando_1) {
        this.operador = operador;
        this.operando_1 = operando_1;
    }
    public Terceto(String operador, String operando_1, String operando_2) {
        this.operador = operador;
        this.operando_1 = operando_1;
        this.operando_2 = operando_2;
    }
    public Terceto(String operador, String operando_1, String operando_2, String tipo) {
        this.operador = operador;
        this.operando_1 = operando_1;
        this.operando_2 = operando_2;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getOperador() {
        return operador;
    }

    public String getOperando_1() {
        return operando_1;
    }

    public String getOperando_2() {
        return operando_2;
    }

    public void setOperando_1(String operando_1) {
        this.operando_1 = operando_1;
    }

    public String getAuxAsm() {
        return auxAsm;
    }

    public void setAuxAsm(String auxAsm) {
        this.auxAsm = auxAsm;
    }

    @Override
    public String toString() {
        return "( " + operador + ", " +  operando_1+ ", " +  operando_2  +" ) " + tipo;
    }
}
