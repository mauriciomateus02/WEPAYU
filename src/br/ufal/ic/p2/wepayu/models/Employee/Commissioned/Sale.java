package br.ufal.ic.p2.wepayu.models.Employee.Commissioned;

import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;

public class Sale {
    private String date;
    private float valor;

    public Sale(String date, float valor) {
        this.date = date;
        this.valor = valor;
    }

    public String getValue() {

        return Conversor.converterInvertedCharacter(String.format("%.2f", valor));

    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {

        return getDate() + ";" + getValue();
    }
}
