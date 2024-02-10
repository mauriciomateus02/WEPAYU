package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;

public class EmpregadoAssalariado extends Empregado {

    private float salarioMensal;

    public EmpregadoAssalariado(String nome, String endereco, String tipo, float salarioMensal)
            throws ExceptionCriarEmpregado {
        super(nome, endereco, tipo);
        this.salarioMensal = salarioMensal;

    }

    // Nesse método é convertido o salario em um numero com 2 casas decimais e
    // retornado o valor
    @Override
    public String getSalario() {

        return String.format("%.2f", salarioMensal);
    }

    @Override
    public String toString() {
        return getNome() + ";" + getEndereco() + ";" + getTipo() + ";" + getSindicalizado() + ";" + getSalario();
    }

}
