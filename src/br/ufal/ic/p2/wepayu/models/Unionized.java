package br.ufal.ic.p2.wepayu.models;

import java.util.ArrayList;

import br.ufal.ic.p2.wepayu.utils.Conversor;

public class Unionized {

    private String employeeID;
    private String unionizedID;
    private Float unionFee;
    // Employee employee;
    private ArrayList<ServiceFee> services = new ArrayList<>();

    public Unionized(String unionizedID, String employeeID, Float unionFee) {
        this.unionizedID = unionizedID;
        this.employeeID = employeeID;
        this.unionFee = unionFee;
    }

    public String getUnionizedID() {
        return unionizedID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getUnionFee() {
        return Conversor.converterInvertedCharacter(String.format("%.2f", unionFee));
    }

    public void setUnionFee(float unionFee) {
        this.unionFee = unionFee;
    }

    public ArrayList<ServiceFee> getServiceFee() {
        return services;
    }

    public void addServiceFee(ServiceFee service) {
        services.add(service);
    }

    @Override
    public String toString() {
        return getUnionizedID() + ";" + getEmployeeID() + ";" + getUnionFee() + "->" + services;
    }
}
