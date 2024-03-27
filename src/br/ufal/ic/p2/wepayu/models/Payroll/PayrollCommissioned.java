package br.ufal.ic.p2.wepayu.models.Payroll;

import java.time.LocalDate;

import br.ufal.ic.p2.wepayu.Exception.DateInvalideException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.controller.employee.SaleController;
import br.ufal.ic.p2.wepayu.controller.humanResources.UnionServiceController;
import br.ufal.ic.p2.wepayu.models.DTO.Payroll.CommissionedDTO;
import br.ufal.ic.p2.wepayu.models.Employee.Commissioned.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.utils.MethodPayment;
import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;
import br.ufal.ic.p2.wepayu.utils.Validator.ValidatorDatePayroll;

public class PayrollCommissioned {

        protected String employeeCommissioned(String key, EmpregadoComissionado empCommissioned,
                        TotalPayroll totalPayroll,
                        LocalDate deadline, int[] day)
                        throws DateInvalideException, ExceptionGetEmpregado, NumberFormatException,
                        EmpregadoNaoExisteException {

                String dateInitial = ValidatorDatePayroll.generateStartDate(deadline, empCommissioned);
                String dateFinal = ValidatorDatePayroll.generateDeadline(deadline);
                String sales_as_string;
                double salaryAmount, salary, netpay, discount = 0.0, sale = 0.0, dividendo, commissionSales = 0.0;

                salaryAmount = totalPayroll.commissoned(key, empCommissioned, deadline, day);

                if (empCommissioned.getSindicalizado()) {
                        discount += Double.parseDouble(empCommissioned.getUnionized().getUnionFee())
                                        * (day[2] + day[0]);
                        double aux = Double.parseDouble(Conversor
                                        .converterInvertedCharacter(UnionServiceController.getServiceFee(key,
                                                        dateInitial, dateFinal)));
                        discount += aux;
                }

                sales_as_string = SaleController.sumOfSalesAmount(key, dateInitial, dateFinal);

                sale = Double.parseDouble(Conversor.converterInvertedCharacter(sales_as_string));

                salary = Double.parseDouble(Conversor.converterInvertedCharacter(empCommissioned.getSalario()));

                dividendo = (double) day[2];

                if (!empCommissioned.getPaymentDay().contains("mensal"))
                        salary = Math.floor((salary * (dividendo / 52D)) * 2D * 100) / 100F;
                salary = ((int) (salary * 100)) / 100.0d;

                if (salary <= 0) {
                        discount = 0.0;
                }
                netpay = salaryAmount - discount;

                if (netpay <= 0) {
                        netpay = 0.0;
                }

                if (sale > 0) {
                        double aux = Double.parseDouble(Conversor
                                        .converterInvertedCharacter(empCommissioned.getComissao()));

                        commissionSales = sale * aux;

                        commissionSales = Math.floor(commissionSales * 100) / 100F;

                }

                String name = Conversor.padRightWithSpaces(empCommissioned.getNome(), 21);
                String salarieFixed = Conversor.padLeftWithSpaces(String.format("%.2f", salary), 9);
                String sales = Conversor.padLeftWithSpaces(sales_as_string, 9);
                String commission = Conversor.padLeftWithSpaces(String.format("%.2f", commissionSales), 9);
                String grossSalary = Conversor.padLeftWithSpaces(String.format("%.2f", salaryAmount), 14);
                String discountAmount = Conversor.padLeftWithSpaces(String.format("%.2f", discount), 10);
                String netpay_as_string = Conversor.padLeftWithSpaces(String.format("%.2f", netpay), 16);
                String methodPayment = MethodPayment.getMethodPayment(empCommissioned);

                CommissionedDTO.addCommissionedDTO(salarieFixed, sales, commission, grossSalary, discountAmount,
                                netpay_as_string);

                return name
                                + salarieFixed
                                + sales
                                + commission
                                + grossSalary
                                + discountAmount
                                + netpay_as_string
                                + methodPayment;
        }

        protected String totalEmployeeCommissioned(String name) {
                name = Conversor.padRightWithSpaces(name, 21);
                String salarieFixed = Conversor
                                .padLeftWithSpaces(String.format("%.2f", CommissionedDTO.getSalarieFixed()), 9);
                String sales = Conversor.padLeftWithSpaces(String.format("%.2f", CommissionedDTO.getSales()), 9);
                String commission = Conversor.padLeftWithSpaces(String.format("%.2f", CommissionedDTO.getCommission()),
                                9);
                String grossSalary = Conversor
                                .padLeftWithSpaces(String.format("%.2f", CommissionedDTO.getSalariedCommissioned()),
                                                14);
                String discountAmount = Conversor
                                .padLeftWithSpaces(String.format("%.2f", CommissionedDTO.getDiscount()), 10);
                String netpay_as_string = Conversor
                                .padLeftWithSpaces(String.format("%.2f", CommissionedDTO.getNetpayCommissioned()), 16);

                return name
                                + salarieFixed
                                + sales
                                + commission
                                + grossSalary
                                + discountAmount
                                + netpay_as_string;

        }

}
