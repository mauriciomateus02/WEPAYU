package br.ufal.ic.p2.wepayu.models.Employee.Hourly;

public class CartaoPontos {

    private String data;
    private float horas;

    public CartaoPontos(String data, float horas) {

        this.data = data;
        this.horas = horas;

    }

    public float gethoras() {

        return horas;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return getData() + ";" + gethoras();
    }
}
