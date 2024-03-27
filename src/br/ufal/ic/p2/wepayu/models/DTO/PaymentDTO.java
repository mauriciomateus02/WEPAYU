package br.ufal.ic.p2.wepayu.models.DTO;

public class PaymentDTO {
    private String employeeID;
    private String name;
    private String bank;
    private String agency;
    private String accountNumber;

    public PaymentDTO(String employeeID, String name) {
        this.employeeID = employeeID;
        this.name = name;
    }

    public PaymentDTO(String employeeID, String name, String bank, String agency, String accountNumber) {
        this.employeeID = employeeID;
        this.name = name;
        this.bank = bank;
        this.agency = agency;
        this.accountNumber = accountNumber;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getName() {
        return name;
    }

    public String getBank() {
        return bank;
    }

    public String getAgency() {
        return agency;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

}
