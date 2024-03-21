package br.ufal.ic.p2.wepayu.models.Unionized;

import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;

public class ServiceFee {

    private String date;
    private float valor;

    public ServiceFee(String date, float valor) {
        this.date = date;
        this.valor = valor;
    }

    public String getDate() {
        return date;
    }

    public String getValue() {
        return Conversor.converterInvertedCharacter(String.format("%.2f", valor));
    }

    @Override
    public String toString() {
        return getDate() + ";" + getValue();
    }
}