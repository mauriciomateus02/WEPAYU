package br.ufal.ic.p2.wepayu.models;

public abstract class Empregado {
    private String nome;
    private String endereco;
    private String tipo;
    private Boolean sindic;

    public Empregado(String nome, String endereco, String tipo) {
        this.nome = nome;
        this.endereco = endereco;
        this.tipo = tipo;
        this.sindic = false;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getTipo() {
        return tipo;
    }

    public Boolean getSindicalizado() {
        return sindic;
    }

    public void setSindicalizado(Boolean sindic) {
        this.sindic = sindic;
    }

    public abstract String getSalario();

    public abstract String toString();
}
