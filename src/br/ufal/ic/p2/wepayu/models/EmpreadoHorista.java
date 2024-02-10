package br.ufal.ic.p2.wepayu.models;

import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;

public class EmpreadoHorista extends Empregado {

    private float salarioHora;

    public EmpreadoHorista(String nome, String endereco, String tipo, float salario)
            throws ExceptionCriarEmpregado {
        super(nome, endereco, tipo);
        this.salarioHora = salario;

    }

    @Override
    public String getSalario() {
        // DecimalFormat format = new DecimalFormat("#.##");
        return String.format("%.2f", salarioHora);

    }

    @Override
    public String toString() {
        return getNome() + ";" + getEndereco() + ";" + getTipo() + ";" + getSindicalizado() + ";" + getSalario();
    }

}
