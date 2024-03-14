package br.ufal.ic.p2.wepayu.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Map;
import java.util.zip.DataFormatException;

import br.ufal.ic.p2.wepayu.Exception.DateInvalideException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.controller.employee.CardController;
import br.ufal.ic.p2.wepayu.controller.employee.EmployeeController;
import br.ufal.ic.p2.wepayu.controller.employee.SaleController;
import br.ufal.ic.p2.wepayu.models.Employee.Employee;
import br.ufal.ic.p2.wepayu.models.Employee.Commissioned.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.Employee.Hourly.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;
import br.ufal.ic.p2.wepayu.utils.EnumType.getEnumActiveTurn;
import br.ufal.ic.p2.wepayu.utils.Validator.Validator;

public class Payroll {

	public static String TotalPayroll(String date)
			throws DateInvalideException, ExceptionGetEmpregado, DataFormatException,
			EmpregadoNaoExisteException {

		try {
			Validator.validatorDate(date, getEnumActiveTurn.Default);

			LocalDate dateVerific = Conversor.converterDate(date, 3);

			LocalDate startDate = null;
			float totalPayroll = 0;
			String salary = "", value = "", dataInitial, datafinal;

			for (Map.Entry<String, Employee> entry : EmployeeController.Empregados.entrySet()) {
				Employee emp = entry.getValue();

				if (emp.getTipo().equals("horista") && dateVerific.getDayOfWeek() == DayOfWeek.FRIDAY) {

					EmpregadoHorista employee = (EmpregadoHorista) emp;

					startDate = dateVerific.minusDays(6);

					dataInitial = startDate.getDayOfMonth() + "/" + startDate.getMonthValue() + "/"
							+ startDate.getYear();
					datafinal = dateVerific.getDayOfMonth() + "/" + dateVerific.getMonthValue()
							+ "/"
							+ dateVerific.getYear();

					// pega o valor das horas normais.

					value = CardController.getHoras(entry.getKey(), "normal", dataInitial,
							datafinal);

					// value = Conversor.converterInvertedCharacter(value);

					salary = Conversor.converterInvertedCharacter(employee.getSalario());

					// soma os valores encontrados

					totalPayroll += Float.parseFloat(value) * Float.parseFloat(salary);

					// pega o valor das horas extras
					value = CardController.getHoras(entry.getKey(), "extra", dataInitial,
							datafinal);

					// adiciona o valor das horas extras

					totalPayroll += Double.parseDouble(value) * Double.parseDouble(salary) * 150 / 100F;

				}

				if (emp.getTipo().equals("comissionado")
						&& dateVerific.get(WeekFields.ISO.weekOfWeekBasedYear()) % 2 == 0) {

					EmpregadoComissionado employee = (EmpregadoComissionado) emp;
					startDate = dateVerific.minusDays(13);

					dataInitial = startDate.getDayOfMonth() + "/" + startDate.getMonthValue() +
							"/"
							+ startDate.getYear();
					datafinal = dateVerific.getDayOfMonth() + "/" + dateVerific.getMonthValue()
							+ "/"
							+ dateVerific.getYear();

					value = SaleController.sumOfSalesAmount(entry.getKey(), dataInitial,
							datafinal);

					value = Conversor.converterInvertedCharacter(value);
					salary = Conversor.converterInvertedCharacter(employee.getSalario());
					String commission = Conversor
							.converterInvertedCharacter(employee.getComissao());
					// soma o valor do empregado comissionado
					totalPayroll += (Float.parseFloat(value) * Float.parseFloat(commission))
							+ Float.parseFloat(salary);

				}
				if (emp.getTipo().equals("assalariado")
						&& dateVerific.getDayOfMonth() == dateVerific.lengthOfMonth()) {

					salary = Conversor.converterInvertedCharacter(emp.getSalario());
					totalPayroll += Float.parseFloat(salary);
				}
			}
			return String.format("%.2f", totalPayroll);
		} catch (Exception e) {
			throw e;
		}
	}
}
