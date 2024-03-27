package br.ufal.ic.p2.wepayu.utils;

import br.ufal.ic.p2.wepayu.models.Employee.Employee;
import br.ufal.ic.p2.wepayu.models.Payment.PaymentInBank;

public class MethodPayment {

    public static String getMethodPayment(Employee emp) {
        if (emp.getMethodPayment().getName().equals("banco")) {
            PaymentInBank payment = (PaymentInBank) emp.getMethodPayment();
            return " " + payment.getBank() + ", Ag. " + payment.getAgency() + " CC "
                    + payment.getAccountNumber();

        } else if (emp.getMethodPayment().getName().equals("correios")) {
            return " Correios," + " " + emp.getEndereco();

        } else {
            return " Em maos";
        }
    }
}
