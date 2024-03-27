package br.ufal.ic.p2.wepayu.models.Payroll;

import java.time.LocalDate;

import br.ufal.ic.p2.wepayu.Exception.DateInvalideException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.controller.humanResources.UnionServiceController;
import br.ufal.ic.p2.wepayu.models.DTO.Payroll.SalariedDTO;
import br.ufal.ic.p2.wepayu.models.Employee.Salaried.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.utils.MethodPayment;
import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;
import br.ufal.ic.p2.wepayu.utils.Validator.ValidatorDatePayroll;

public class PayrollSalaried {

    protected String employeeSalaried(String key, EmpregadoAssalariado empSalaried, TotalPayroll totalPayroll,
            LocalDate deadline)
            throws DateInvalideException, NumberFormatException, ExceptionGetEmpregado, EmpregadoNaoExisteException {

        String dateInitial = ValidatorDatePayroll.generateStartDate(deadline, empSalaried);
        String dateFinal = ValidatorDatePayroll.generateDeadline(deadline);

        double salary, netpay, discount = 0.0;

        int[] day = ValidatorDatePayroll.getPaymentEmployee(empSalaried.getPaymentDay(), deadline);
        if (empSalaried.getPaymentDay().contains("mensal")) {
            salary = totalPayroll.salaried(empSalaried, 0);
        } else {
            double dividendo = (double) day[2];
            salary = totalPayroll.salaried(empSalaried, dividendo);
        }

        if (empSalaried.getSindicalizado()) {
            discount = Double.parseDouble(empSalaried.getUnionized().getUnionFee()) * (day[0] + 1);

            double aux = Double.parseDouble(Conversor
                    .converterInvertedCharacter(UnionServiceController.getServiceFee(key, dateInitial, dateFinal)));
            discount += aux;
        }
        if (salary <= 0) {
            discount = 0.0;
        }

        netpay = salary - discount;

        if (netpay <= 0)
            netpay = 0.0;

        String name = Conversor.padRightWithSpaces(empSalaried.getNome(), 48);
        String salarieTotal = Conversor.padLeftWithSpaces(String.format("%.2f", salary), 14);
        String discountTotal = Conversor.padLeftWithSpaces(String.format("%.2f", discount), 10);
        String netpay_as_string = Conversor.padLeftWithSpaces(String.format("%.2f", netpay), 16);
        String methodPayment = MethodPayment.getMethodPayment(empSalaried);

        SalariedDTO.addSalariedDTO(salarieTotal, discountTotal, netpay_as_string);

        return name
                + salarieTotal
                + discountTotal
                + netpay_as_string
                + methodPayment;
    }

    protected String totalEmployeeSalaried(String name) {
        name = Conversor.padRightWithSpaces(name, 48);
        String salarieTotal = Conversor.padLeftWithSpaces(String.format("%.2f", SalariedDTO.getSalaried()), 14);
        String discountTotal = Conversor.padLeftWithSpaces(String.format("%.2f", SalariedDTO.getDiscountSalaried()),
                10);
        String netpay_as_string = Conversor.padLeftWithSpaces(String.format("%.2f", SalariedDTO.getNetpaySalaried()),
                16);

        return name
                + salarieTotal
                + discountTotal
                + netpay_as_string;

    }
}
