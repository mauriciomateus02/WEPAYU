package br.ufal.ic.p2.wepayu.controller.employee;

import java.time.LocalDate;

import br.ufal.ic.p2.wepayu.Exception.DateInvalideException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.Exception.SaleAmountException;
import br.ufal.ic.p2.wepayu.models.Employee.Commissioned.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.Employee.Commissioned.Sale;
import br.ufal.ic.p2.wepayu.utils.Validator.Validator;
import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;
import br.ufal.ic.p2.wepayu.utils.EnumType.EnumContract;
import br.ufal.ic.p2.wepayu.utils.EnumType.getEnumActiveTurn;
import br.ufal.ic.p2.wepayu.utils.Validator.ValidatorEmployee;
import br.ufal.ic.p2.wepayu.utils.Validator.ValidatorSale;

public class SaleController {

    public static void registerSale(String emp, String date, String value)
            throws DateInvalideException, SaleAmountException, ExceptionGetEmpregado {

        // valida se a data está correta
        Validator.validatorDate(date, getEnumActiveTurn.Default);
        // valida se a quantia é válida
        ValidatorSale.validateSaleAmount(value);

        // valida se o empragado é tipo commisionado
        ValidatorEmployee.EmployeeType(emp, EnumContract.COMMISSiONED);

        // converte numero do tipo 20,00 em 20.00
        value = Conversor.converterInvertedCharacter(value);

        Sale sale = new Sale(date, Float.parseFloat(value));

        EmpregadoComissionado empregado = (EmpregadoComissionado) EmployeeController.Empregados.get(emp);

        empregado.addSale(sale);

        EmployeeController.Empregados.put(emp, empregado);

    }

    public static String sumOfSalesAmount(String emp, String startDate, String deadline)
            throws DateInvalideException, ExceptionGetEmpregado {

        ValidatorEmployee.EmployeeType(emp, EnumContract.COMMISSiONED);
        Validator.validatorDate(startDate, getEnumActiveTurn.InitialDate);
        Validator.validatorDate(deadline, getEnumActiveTurn.DeadLine);
        Validator.validateSearchDate(startDate, deadline);

        EmpregadoComissionado employee = (EmpregadoComissionado) EmployeeController.Empregados.get(emp);

        if (employee.getSale().size() == 0) {
            return "0,00";
        } else {

            float amount = 0;

            LocalDate dateFinal = null, dateVerific = null, dateInitial = null;
            String temp;
            // converte a string em data para ser analisada.
            dateFinal = Conversor.converterDate(deadline, 2);

            dateInitial = Conversor.converterDate(startDate, 1);

            for (Sale sale : employee.getSale()) {
                // tipo 3 é conversão de data de forma nomal
                dateVerific = Conversor.converterDate(sale.getDate(), 3);
                temp = sale.getValue();
                if (Validator.validatorRangeDate(dateInitial, dateVerific, dateFinal)) {
                    if (temp.contains(","))
                        temp = Conversor.converterInvertedCharacter(temp);
                    amount += Float.parseFloat(temp);
                }
            }
            // ao somar o valor resultante é convertido para duas casas decimais é
            // convertido o . em ,
            String amount_as_string = String.format("%.2f", amount);
            return amount_as_string;
            // Valor formatado em duas casas decimais e retornado como string com vírgula
        }
    }

}
