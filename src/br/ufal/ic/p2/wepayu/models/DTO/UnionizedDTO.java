package br.ufal.ic.p2.wepayu.models.DTO;

public class UnionizedDTO {
    private String index;
    private String employeeID;
    private float unionFee;
    private String[] listService;

    public UnionizedDTO(String index, String employeeID, float unionFee, String[] listService) {
        this.index = index;
        this.employeeID = employeeID;
        this.unionFee = unionFee;
        this.listService = listService;
    }

    public UnionizedDTO(String index, String employeeID, float unionFee) {
        this.index = index;
        this.employeeID = employeeID;
        this.unionFee = unionFee;
    }

    public String getIndex() {
        return index;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public float getUnionFee() {
        return unionFee;
    }

    public String[] getListService() {
        return listService;
    }
}
