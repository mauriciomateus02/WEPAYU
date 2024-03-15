package br.ufal.ic.p2.wepayu.models.Employee;

import br.ufal.ic.p2.wepayu.models.Payment.Payment;
import br.ufal.ic.p2.wepayu.models.Unionized.Unionized;

public abstract class Employee {
    private String nome;
    private String endereco;
    private String tipo;
    private String paymentDay;
    private Boolean sindic;
    private Unionized union;
    private Payment methodPayment;

    public Employee(String nome, String endereco, String tipo) {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.sindic = false;
    }

    public String getNome() {
        return nome;
    }

    public String getPaymentDay() {
        return paymentDay;
    }

    public void setPaymentDay(String payment) {
        paymentDay = payment;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTipo() {
        return tipo;
    }

    public Boolean getSindicalizado() {
        return sindic;
    }

    public void setSindicalizado(Boolean sindic) {
        this.sindic = sindic;
    }

    public Unionized getUnionized() {
        return union;
    }

    public Payment getMethodPayment() {
        return methodPayment;
    }

    public void setUnionized(Unionized union) {
        this.union = union;
    }

    public void setMethodPayment(Payment method) {
        this.methodPayment = method;
    }

    public abstract String getSalario();

    public abstract void setSalario(float salario);

    public abstract String toString();
}
