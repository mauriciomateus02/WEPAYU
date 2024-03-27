package br.ufal.ic.p2.wepayu.models.DTO;

public class EmployeeDTO {

    private String index;
    private String name;
    private String address;
    private String type;
    private String unionized;
    private float salary;
    private float commission;
    private String paymentDay;
    private String[] list;

    public EmployeeDTO(String index, String name, String address, String type, String unionized, float salary,
            String paymentDay) {
        this.index = index;
        this.name = name;
        this.address = address;
        this.type = type;
        this.unionized = unionized;
        this.salary = salary;
        this.paymentDay = paymentDay;
    }

    public EmployeeDTO(String index, String name, String address, String type, String unionized, float salary,
            float commission, String paymentDay) {
        this.index = index;
        this.name = name;
        this.address = address;
        this.type = type;
        this.unionized = unionized;
        this.salary = salary;
        this.commission = commission;
        this.paymentDay = paymentDay;
    }

    public EmployeeDTO(String index, String name, String address, String type, String unionized, float salary,
            String paymentDay, String[] list) {
        this.index = index;
        this.name = name;
        this.address = address;
        this.type = type;
        this.unionized = unionized;
        this.salary = salary;
        this.paymentDay = paymentDay;
        this.list = list;
    }

    public EmployeeDTO(String index, String name, String address, String type, String unionized, float salary,
            float commission, String paymentDay, String[] list) {
        this.index = index;
        this.name = name;
        this.address = address;
        this.type = type;
        this.unionized = unionized;
        this.salary = salary;
        this.commission = commission;
        this.paymentDay = paymentDay;
        this.list = list;
    }

    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getType() {
        return type;
    }

    public String getUnionized() {
        return unionized;
    }

    public float getSalary() {
        return salary;
    }

    public float getCommission() {
        return commission;
    }

    public String getPaymentDay() {
        return paymentDay;
    }

    public String[] getList() {
        return list;
    }

}
