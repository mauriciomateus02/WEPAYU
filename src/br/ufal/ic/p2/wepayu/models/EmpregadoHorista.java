package br.ufal.ic.p2.wepayu.models;

import java.util.ArrayList;
import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;

public class EmpregadoHorista extends Employee {

    private float salarioHora;
    private ArrayList<CartaoPontos> cartaoPontos = new ArrayList<>();

    public EmpregadoHorista(String nome, String endereco, String tipo, float salario)
            throws ExceptionCriarEmpregado {
        super(nome, endereco, tipo);
        this.salarioHora = salario;

    }

    public ArrayList<CartaoPontos> getCartaoPontos() {
        return cartaoPontos;
    }

    public void addCartaoPontos(CartaoPontos cartao) {
        cartaoPontos.add(cartao);
    }

    @Override
    public String getSalario() {
        // DecimalFormat format = new DecimalFormat("#.##");
        return String.format("%.2f", salarioHora);

    }

    @Override
    public String toString() {
        return getNome() + ";" + getEndereco() + ";" + getTipo() + ";" + getSindicalizado() + ";" + getSalario() + ";"
                + "->" + cartaoPontos;
    }

}