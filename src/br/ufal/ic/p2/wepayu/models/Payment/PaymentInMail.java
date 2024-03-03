package br.ufal.ic.p2.wepayu.models.Payment;

public class PaymentInMail extends Payment {

    public PaymentInMail(String employeeID, String name) {
        super(employeeID, name);

    }

    @Override
    public String pay() {
        return "";
    }

    @Override
    public String toString() {
        return getEmployeeID() + ";" + getName() + "->[]";
    }

}
