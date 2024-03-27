package br.ufal.ic.p2.wepayu.models.Payroll;

import java.time.LocalDate;
import java.util.zip.DataFormatException;

import br.ufal.ic.p2.wepayu.Exception.DateInvalideException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.controller.employee.CardController;
import br.ufal.ic.p2.wepayu.controller.humanResources.UnionServiceController;
import br.ufal.ic.p2.wepayu.models.DTO.Payroll.HourlyDTO;
import br.ufal.ic.p2.wepayu.models.Employee.Hourly.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.utils.MethodPayment;
import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;
import br.ufal.ic.p2.wepayu.utils.Validator.ValidatorDatePayroll;

public class PayrollHourly {

    protected String employeeHourly(String Key, EmpregadoHorista empHourly, TotalPayroll totalPayroll,
            LocalDate deadline)
            throws ExceptionGetEmpregado, DataFormatException, DateInvalideException, EmpregadoNaoExisteException {

        String dateInitial = ValidatorDatePayroll.generateStartDate(deadline, empHourly);
        String dateFinal = ValidatorDatePayroll.generateDeadline(deadline);
        double salary, netpay, discount = 0.0;

        // Claudia Abreu 15 2 198,00 7,00 191,00 Em maos
        int[] day = ValidatorDatePayroll.getPaymentEmployee(empHourly.getPaymentDay(), deadline);
        salary = totalPayroll.hourly(Key, empHourly, deadline, day);

        if (empHourly.getSindicalizado()) {
            discount = Double.parseDouble(empHourly.getUnionized().getUnionFee()) * (day[2] + day[0]);
            double aux = Double.parseDouble(Conversor
                    .converterInvertedCharacter(UnionServiceController.getServiceFee(Key, dateInitial, dateFinal)));
            discount += aux;
        }
        if (salary <= 0) {
            discount = 0.0;
        }
        netpay = salary - discount;

        if (netpay <= 0)
            netpay = 0.0;

        String name = Conversor.padRightWithSpaces(empHourly.getNome(), 37);
        String hourly = Conversor.padLeftWithSpaces(getHourly(Key, "normal", dateInitial, dateFinal), 5);
        String hourlyExtra = Conversor.padLeftWithSpaces(getHourly(Key, "extra", dateInitial, dateFinal), 6);
        String salarieTotal = Conversor.padLeftWithSpaces(String.format("%.2f", salary), 14);
        String discountTotal = Conversor.padLeftWithSpaces(String.format("%.2f", discount), 10);
        String netpay_as_string = Conversor.padLeftWithSpaces(String.format("%.2f", netpay), 16);
        String methodPayment = MethodPayment.getMethodPayment(empHourly);

        HourlyDTO.addHourlyDTO(hourly, hourlyExtra, salarieTotal, discountTotal, netpay_as_string);

        return name
                + hourly
                + hourlyExtra
                + salarieTotal
                + discountTotal
                + netpay_as_string
                + methodPayment;

    }

    private String getHourly(String emp, String type, String dateInitial, String dateFinal)
            throws ExceptionGetEmpregado, DataFormatException, DateInvalideException, EmpregadoNaoExisteException {

        return CardController.getHoras(emp, type, dateInitial, dateFinal);
    }

    protected String totalEmployeesHourly(String name) {
        name = Conversor.padRightWithSpaces(name, 37);
        String hourly = Conversor.padLeftWithSpaces(Integer.toString(HourlyDTO.getHourly()), 5);
        String hourlyExtra = Conversor.padLeftWithSpaces(Integer.toString(HourlyDTO.getHourlyExtra()), 6);
        String salarieTotal = Conversor.padLeftWithSpaces(String.format("%.2f", HourlyDTO.getSalarieHourly()), 14);
        String discountTotal = Conversor.padLeftWithSpaces(String.format("%.2f", HourlyDTO.getDiscountHourly()), 10);
        String netpay_as_string = Conversor.padLeftWithSpaces(String.format("%.2f", HourlyDTO.getNetpayHourly()), 16);

        return name
                + hourly
                + hourlyExtra
                + salarieTotal
                + discountTotal
                + netpay_as_string;

    }

}
