package br.ufal.ic.p2.wepayu.Exception;

public class ExceptionRemoveEmpregado extends Exception {
    public ExceptionRemoveEmpregado() {
        super("Identificacao do empregado nao pode ser nula.");
    }
}
