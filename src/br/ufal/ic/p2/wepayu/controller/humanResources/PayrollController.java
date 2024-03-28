package br.ufal.ic.p2.wepayu.controller.humanResources;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.DataFormatException;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.models.Payroll.Payroll;
import br.ufal.ic.p2.wepayu.models.Payroll.TotalPayroll;
import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;

public class PayrollController {

    public static ArrayList<String> PaymentDays;
    public static HashMap<String, ArrayList<String>> PaymentDay;

    public static String totalPayroll(String data)
            throws ExceptionGetEmpregado, DateInvalideException, DataFormatException, EmpregadoNaoExisteException {

        if (data.isEmpty())
            throw new ExceptionGetEmpregado("Data deve ser nao nulo.");

        TotalPayroll totalPay = new TotalPayroll(data);

        return Conversor.converterCharacter(totalPay.getPayroll());
    }

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

    public static void generatePayroll(String outfile, String deadline)
            throws DateInvalideException, FileNotFoundException, ExceptionGetEmpregado, DataFormatException,
            EmpregadoNaoExisteException {

        Payroll payroll = new Payroll();

        payroll.generatePayroll(outfile, deadline);

    }

}
