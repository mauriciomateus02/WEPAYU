package br.ufal.ic.p2.wepayu.models.Payment;

public class PaymentInHands extends Payment {

    public PaymentInHands(String employeeID, String name) {
        super(employeeID, name);
    }

    @Override
    public String pay() {
        return "";
    }

    @Override
    public String toString() {
        return getEmployeeID() + ";" + getName();
    }

}
