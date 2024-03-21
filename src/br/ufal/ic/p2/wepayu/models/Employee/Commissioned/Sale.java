package br.ufal.ic.p2.wepayu.models.Employee.Commissioned;

import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;

public class Sale {
    private String date;
    private float value;

    public Sale(String date, float value) {
        this.date = date;
        this.value = value;
    }

    public String getValue() {

        return Conversor.converterInvertedCharacter(String.format("%.2f", value));

    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {

        return getDate() + ";" + getValue();
    }
}
