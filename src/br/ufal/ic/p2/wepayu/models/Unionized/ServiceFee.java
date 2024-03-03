package br.ufal.ic.p2.wepayu.models.Unionized;

import br.ufal.ic.p2.wepayu.utils.Conversor;

public class ServiceFee {

    private String date;
    private float value;

    public ServiceFee(String date, float value) {
        this.date = date;
        this.value = value;
    }

    public String getDate() {
        return date;
    }

    public String getValue() {
        return Conversor.converterInvertedCharacter(String.format("%.2f", value));
    }

    @Override
    public String toString() {
        return getDate() + ";" + getValue();
    }
}