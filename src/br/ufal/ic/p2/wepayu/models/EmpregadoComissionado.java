package br.ufal.ic.p2.wepayu.models;

import java.util.ArrayList;

import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;

public class EmpregadoComissionado extends Employee {

    private float salario;
    private float comissao;
    private ArrayList<Sale> sales = new ArrayList<>();

    public EmpregadoComissionado(String nome, String endereco, String tipo, float salario, float comissao)
            throws ExceptionCriarEmpregado {

        super(nome, endereco, tipo);
        this.comissao = comissao;
        this.salario = salario;

    }

    @Override
    public String getSalario() {
        return String.format("%.2f", salario);
    }

    public String getComissao() {
        return String.format("%.2f", comissao);
    }

    public float getSalarioTotal() {
        return salario + comissao;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public void setComissao(float comissao) {
        this.comissao = comissao;
    }

    public ArrayList<Sale> getSale() {
        return sales;
    }

    public void addSale(Sale sale) {
        sales.add(sale);
    }

    @Override
    public String toString() {
        return getNome() + ";" + getEndereco() + ";" + getTipo() + ";" + getSindicalizado() + ";" + getSalario() + ";"
                + getComissao() + "->" + sales;
    }
}
