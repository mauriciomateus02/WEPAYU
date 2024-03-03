package br.ufal.ic.p2.wepayu.models.Payment;

public class PaymentInBank extends Payment {

    private String bank;
    private String agency;
    private String accountNumber;

    public PaymentInBank(String employeeID, String name, String bank, String agency, String accountNumber) {
        super(employeeID, name);
        this.bank = bank;
        this.agency = agency;
        this.accountNumber = accountNumber;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String pay() {
        return "";
    }

    @Override
    public String toString() {
        return getEmployeeID() + ";" + getName() + ";" + getBank() + ";" + getAgency() + ";"
                + getAccountNumber() + "->[]";
    }

}
