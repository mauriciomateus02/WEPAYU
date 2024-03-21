package br.ufal.ic.p2.wepayu.models.Unionized;

import java.util.ArrayList;

import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;

public class Unionized {

    private String employeeID;
    private String IDsindicalizado;
    private Float taxaSindicato;
    // Employee employee;
    private ArrayList<ServiceFee> services = new ArrayList<>();

    public Unionized(String IDsindicalizado, String employeeID, Float taxaSindicato) {
        this.IDsindicalizado = IDsindicalizado;
        this.employeeID = employeeID;
        this.taxaSindicato = taxaSindicato;
    }

    public String getIDsindicalizado() {
        return IDsindicalizado;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String gettaxaSindicato() {
        return Conversor.converterInvertedCharacter(String.format("%.2f", taxaSindicato));
    }

    public void settaxaSindicato(float taxaSindicato) {
        this.taxaSindicato = taxaSindicato;
    }

    public ArrayList<ServiceFee> getServiceFee() {
        return services;
    }

    public void addServiceFee(ServiceFee service) {
        services.add(service);
    }

    @Override
    public String toString() {
        return getIDsindicalizado() + ";" + getEmployeeID() + ";" + gettaxaSindicato() + "->" + services;
    }
}
