package br.ufal.ic.p2.wepayu.models.Payment;

public class PagamentoEmBanco extends Payment {

    private String banco;
    private String agencia;
    private String numeroConta;

    public PagamentoEmBanco(String employeeID, String name, String banco, String agencia, String numeroConta) {
        super(employeeID, name);
        this.banco = banco;
        this.agencia = agencia;
        this.numeroConta = numeroConta;
    }

    public String getbanco() {
        return banco;
    }

    public void setbanco(String banco) {
        this.banco = banco;
    }

    public String getagencia() {
        return agencia;
    }

    public void setagencia(String agencia) {
        this.agencia = agencia;
    }

    public String getnumeroConta() {
        return numeroConta;
    }

    public void setnumeroConta(String numeroConta) {
        this.numeroConta = numeroConta;
    }

    @Override
    public String pay() {
        return "";
    }

    @Override
    public String toString() {
        return getEmployeeID() + ";" + getName() + ";" + getbanco() + ";" + getagencia() + ";"
                + getnumeroConta();
    }

}
