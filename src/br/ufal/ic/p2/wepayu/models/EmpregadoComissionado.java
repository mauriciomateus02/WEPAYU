package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;

public class EmpregadoComissionado extends Empregado {

    private float salario;
    private float comissao;

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

    @Override
    public String toString() {
        return getNome() + ";" + getEndereco() + ";" + getTipo() + ";" + getSindicalizado() + ";" + getSalario() + ";"
                + getComissao();
    }
}
