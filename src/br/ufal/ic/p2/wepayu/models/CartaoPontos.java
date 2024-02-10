package br.ufal.ic.p2.wepayu.models;

public class CartaoPontos {

    private String data;
    private float horas;

    public CartaoPontos(String data, float horas) {

        this.data = data;
        this.horas = horas;

    }

    public String gethoras() {

        return Float.toString(horas);
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return getData() + ";" + gethoras();
    }
}
