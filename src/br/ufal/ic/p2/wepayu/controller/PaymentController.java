package br.ufal.ic.p2.wepayu.controller;

import java.util.HashMap;

import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.models.Payment.Payment;
import br.ufal.ic.p2.wepayu.models.Payment.PaymentInBank;
import br.ufal.ic.p2.wepayu.models.Payment.PaymentInHands;
import br.ufal.ic.p2.wepayu.models.Payment.PaymentInMail;

public class PaymentController {

    public static HashMap<String, Payment> methodsPayment;

    public static void MethodPayment(String employeeID, String method) throws ExceptionGetEmpregado {

        Payment payment;
        switch (method) {
            case "emMaos":
                // cria o metodo de pagamento
                payment = new PaymentInHands(employeeID, method);
                // salva o metodo de pagamento
                methodsPayment.put(employeeID, payment);
                // vincula o metodo de pagamento ao cliente
                EmployeeController.Empregados.get(employeeID).setMethodPayment(payment);
                break;
            case "correios":
                // cria o metodo de pagamento
                payment = new PaymentInMail(employeeID, method);
                // salva o metodo de pagamento
                methodsPayment.put(employeeID, payment);
                // vincula o metodo de pagamento ao cliente
                EmployeeController.Empregados.get(employeeID).setMethodPayment(payment);
                break;
            default:
                throw new ExceptionGetEmpregado("Metodo de pagamento invalido.");
        }
        methodsPayment.put(employeeID, payment);
    }

    public static void MethodPayment(String employeeID, String method, String bank, String agency,
            String accountNumber) throws ExceptionGetEmpregado {

        Payment payment;
        // verifica se os atributos foram enviados corretamente
        validateAttribut(method, bank, agency, accountNumber);

        if (method.equals("banco")) {
            // cria o método de pagamento
            payment = new PaymentInBank(employeeID, method, bank, agency, accountNumber);
            // salva o método de pagamento
            methodsPayment.put(employeeID, payment);
            // vincula o método de pagamento ao cliente
            EmployeeController.Empregados.get(employeeID).setMethodPayment(payment);

        } else {
            throw new ExceptionGetEmpregado("Atributo nao existe.");
        }

        methodsPayment.put(employeeID, payment);
    }

    private static void validateAttribut(String method, String bank, String agency, String accountNumber)
            throws ExceptionGetEmpregado {

        if (method.isEmpty()) {
            throw new ExceptionGetEmpregado("Metodo de pagamento invalido.");
        }
        if (bank.isEmpty()) {
            throw new ExceptionGetEmpregado("Banco nao pode ser nulo.");
        }
        if (agency.isEmpty()) {
            throw new ExceptionGetEmpregado("Agencia nao pode ser nulo.");
        }
        if (accountNumber.isEmpty()) {
            throw new ExceptionGetEmpregado("Conta corrente nao pode ser nulo.");
        }

    }

}
