package br.ufal.ic.p2.wepayu.controller.humanResources;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.zip.DataFormatException;

import br.ufal.ic.p2.wepayu.Exception.DateInvalideException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.controller.employee.EmployeeController;
import br.ufal.ic.p2.wepayu.models.Employee.Employee;
import br.ufal.ic.p2.wepayu.utils.Payroll;
import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;

public class PayrollController {

    public static String totalPayroll(String data)
            throws ExceptionGetEmpregado, DateInvalideException, DataFormatException, EmpregadoNaoExisteException {
        if (data.isEmpty())
            throw new ExceptionGetEmpregado("Data deve ser nao nulo.");

        return Payroll.TotalPayroll(data);
    }

    public static void getPayroll(String emp, String date) {
        // pega o empregado que será analisado
        Employee employee = EmployeeController.Empregados.get(emp);

    }

    private float discount(String employeeID, Employee employee, String date)
            throws DateInvalideException, ExceptionGetEmpregado, EmpregadoNaoExisteException {

        LocalDate deadline = Conversor.converterDate(date, 3);
        LocalDate startDate;
        float deductions = 0;
        String auxFee;

        if (employee.getSindicalizado()) {

            if (employee.getTipo().equals("horista") && deadline.getDayOfWeek() == DayOfWeek.FRIDAY) {
                // volta uma semana para verificar os valores gastos
                startDate = deadline.minusDays(6);

                auxFee = UnionServiceController.getServiceFee(employeeID, startDate.toString(), deadline.toString());

                deductions += Float.parseFloat(auxFee) + (Float.parseFloat(employee.getUnionized().getUnionFee()) * 6);
            }
            if (employee.getTipo().equals("comissionado")
                    && deadline.get(WeekFields.ISO.weekOfWeekBasedYear()) % 2 == 0) {
                // volta uma semana para verificar os valores gastos
                startDate = deadline.minusDays(6);

                auxFee = UnionServiceController.getServiceFee(employeeID, startDate.toString(), deadline.toString());

                deductions += Float.parseFloat(auxFee) + (Float.parseFloat(employee.getUnionized().getUnionFee()) * 6);
            }
            if (employee.getTipo().equals("assalariado") && deadline.getDayOfMonth() == deadline.lengthOfMonth()) {
                // volta uma semana para verificar os valores gastos
                startDate = deadline.minusDays(6);

                auxFee = UnionServiceController.getServiceFee(employeeID, startDate.toString(), deadline.toString());

                deductions += Float.parseFloat(auxFee) + (Float.parseFloat(employee.getUnionized().getUnionFee()) * 6);
            }
            return deductions;
        } else {
            return deductions;
        }
    }
}
