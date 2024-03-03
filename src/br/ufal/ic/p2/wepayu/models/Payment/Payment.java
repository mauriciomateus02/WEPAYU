package br.ufal.ic.p2.wepayu.models.Payment;

public abstract class Payment {

    private String name;
    private String employeeID;

    public Payment(String employeeID, String name) {
        this.employeeID = employeeID;
        this.name = name;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setMethodPayment(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract String pay();

    public abstract String toString();
}
