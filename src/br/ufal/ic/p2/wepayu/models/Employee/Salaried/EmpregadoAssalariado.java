package br.ufal.ic.p2.wepayu.models.Employee.Salaried;

import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;
import br.ufal.ic.p2.wepayu.models.Employee.Employee;
import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;

public class EmpregadoAssalariado extends Employee {

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
        return getNome() + ";" + getEndereco() + ";" + getTipo() + ";" + getSindicalizado() + ";"
                + Conversor.converterInvertedCharacter(getSalario());
    }

    @Override
    public void setSalario(float salario) {
        this.salarioMensal = salario;
    }

}
