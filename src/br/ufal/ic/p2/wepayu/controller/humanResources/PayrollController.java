package br.ufal.ic.p2.wepayu.controller.humanResources;

import java.util.ArrayList;
import java.util.zip.DataFormatException;

import br.ufal.ic.p2.wepayu.Exception.DateInvalideException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionCreatePaymentDay;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
// import br.ufal.ic.p2.wepayu.controller.employee.EmployeeController;
// import br.ufal.ic.p2.wepayu.models.Employee.Employee;
import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;
import br.ufal.ic.p2.wepayu.utils.Payroll;

public class PayrollController {

    public static ArrayList<String> PaymentDays;

    public static String totalPayroll(String data)
            throws ExceptionGetEmpregado, DateInvalideException, DataFormatException, EmpregadoNaoExisteException {

        if (data.isEmpty())
            throw new ExceptionGetEmpregado("Data deve ser nao nulo.");

        return Conversor.converterCharacter(Payroll.TotalPayroll(data));
    }

    // public static void getPayroll(String emp, String date) {
    // // pega o empregado que será analisado
    // Employee employee = EmployeeController.Empregados.get(emp);

    // }

    public static void createPaymentDay(String day) throws ExceptionCreatePaymentDay {
        if (day.isEmpty()) {
            throw new ExceptionCreatePaymentDay("Descricao de agenda nao pode ser nula");
        }

        if (day.contains(" ")) {

            String[] agenda = day.split(" ");
            int dayValidator, WeekValidator;

            if (agenda.length == 2) {
                if (agenda[0].equals("semanal")) {
                    dayValidator = Integer.parseInt(agenda[1]);
                    if (dayValidator <= 0 || dayValidator > 7) {
                        throw new ExceptionCreatePaymentDay("Descricao de agenda invalida");
                    }
                    if (PaymentDays.contains(day)) {
                        throw new ExceptionCreatePaymentDay("Agenda de pagamentos ja existe");
                    }
                    PaymentDays.add(day);
                } else if (agenda[0].equals("mensal")) {

                    if (PaymentDays.contains(day)) {
                        throw new ExceptionCreatePaymentDay("Agenda de pagamentos ja existe");
                    }

                    dayValidator = Integer.parseInt(agenda[1]);

                    if (dayValidator <= 0 || dayValidator > 28) {
                        throw new ExceptionCreatePaymentDay("Descricao de agenda invalida");
                    }

                    PaymentDays.add(day);
                }
            } else {
                if (agenda[0].equals("semanal")) {

                    WeekValidator = Integer.parseInt(agenda[1]);
                    dayValidator = Integer.parseInt(agenda[2]);

                    if ((WeekValidator <= 0 || WeekValidator > 52) || (dayValidator <= 0 || dayValidator > 7)) {
                        throw new ExceptionCreatePaymentDay("Descricao de agenda invalida");
                    }
                    if (PaymentDays.contains(day)) {
                        throw new ExceptionCreatePaymentDay("Agenda de pagamentos ja existe");
                    }

                    PaymentDays.add(day);

                } else {
                    throw new ExceptionCreatePaymentDay("Descricao de agenda invalida");
                }
            }
        } else {
            throw new ExceptionCreatePaymentDay("Descricao de agenda invalida");
        }

    }

    public static void resetPaymentDay() {
        PaymentDays.add("semanal 5");
        PaymentDays.add("mensal $");
        PaymentDays.add("semanal 2 5");

    }

}
