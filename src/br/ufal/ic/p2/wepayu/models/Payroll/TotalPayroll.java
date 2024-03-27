package br.ufal.ic.p2.wepayu.models.Payroll;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
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
import br.ufal.ic.p2.wepayu.utils.Validator.ValidatorDatePayroll;

public class TotalPayroll {

	private String payroll;

	public TotalPayroll(String date)
			throws DateInvalideException, ExceptionGetEmpregado, DataFormatException,
			EmpregadoNaoExisteException {

		try {

			Validator.validatorDate(date, getEnumActiveTurn.Default);

			LocalDate dateVerific = Conversor.converterDate(date, 3);
			LocalDate contraction = LocalDate.of(2005, 1, 1);

			float totalPayroll = 0;

			Long check = ChronoUnit.WEEKS.between(contraction, dateVerific);

			for (Map.Entry<String, Employee> entry : EmployeeController.Empregados.entrySet()) {
				Employee emp = entry.getValue();

				int[] day = ValidatorDatePayroll.getPaymentEmployee(emp.getPaymentDay(), dateVerific);

				if (emp.getPaymentDay().contains("mensal")
						&& ValidatorDatePayroll.getPaymentMonth(day[1], dateVerific)) {
					if (emp.getTipo().equals("assalariado")) {

						totalPayroll += salaried(emp, 0);
					}
					if (emp.getTipo().equals("comissionado")) {

						double total = commissoned(entry.getKey(), emp, dateVerific, day);
						totalPayroll += total;
					}
					if (emp.getTipo().equals("horista")) {
						double total = hourly(entry.getKey(), emp, dateVerific, day);
						totalPayroll += total;
					}
				} else if (emp.getPaymentDay().contains("semanal")
						&& ValidatorDatePayroll.getPaymentWeek(check + 1, day[0], day[1], dateVerific)) {

					if (emp.getTipo().equals("assalariado")) {

						double dividendo = (double) day[2];
						totalPayroll += salaried(emp, dividendo);
					}

					if (emp.getTipo().equals("comissionado")) {

						double total = commissoned(entry.getKey(), emp, dateVerific, day);
						totalPayroll += total;
					}
					if (emp.getTipo().equals("horista")) {
						double total = hourly(entry.getKey(), emp, dateVerific, day);
						totalPayroll += total;
					}

				}

			}
			payroll = String.format("%.2f", totalPayroll);
		} catch (Exception e) {
			throw e;
		}
	}

	public double commissoned(String key, Employee emp, LocalDate dateVerific, int[] day)
			throws DateInvalideException, ExceptionGetEmpregado {

		EmpregadoComissionado employee = (EmpregadoComissionado) emp;
		LocalDate startDate = null;
		String dataInitial, datafinal, value, salary;

		if (emp.getPaymentDay().contains("mensal"))
			startDate = dateVerific.minusDays(day[1] - 1);
		else
			startDate = dateVerific.minusDays((day[0] * 7) - 1);

		dataInitial = startDate.getDayOfMonth() + "/" + startDate.getMonthValue() +
				"/"
				+ startDate.getYear();
		datafinal = dateVerific.getDayOfMonth() + "/" + dateVerific.getMonthValue()
				+ "/"
				+ dateVerific.getYear();

		value = SaleController.sumOfSalesAmount(key, dataInitial, datafinal);

		value = Conversor.converterInvertedCharacter(value);
		salary = Conversor.converterInvertedCharacter(employee.getSalario());
		String commission = Conversor.converterInvertedCharacter(employee.getComissao());

		// soma o valor do empregado comissionado
		Double salarioAmount = Double.parseDouble(salary);

		Double commissionAmount = (Double.parseDouble(value) * Double.parseDouble(commission));
		double dividendo = (double) day[2];
		if (!emp.getPaymentDay().contains("mensal"))
			salarioAmount = Math.floor((salarioAmount * (dividendo / 52D)) * 2D * 100) / 100F;
		salarioAmount = ((int) (salarioAmount * 100)) / 100.0d;

		commissionAmount = Math.floor(commissionAmount * 100) / 100F;

		return salarioAmount + commissionAmount;
	}

	public double hourly(String key, Employee emp, LocalDate dateVerific, int[] day)
			throws ExceptionGetEmpregado, DataFormatException, DateInvalideException, EmpregadoNaoExisteException {

		EmpregadoHorista employee = (EmpregadoHorista) emp;
		LocalDate startDate;
		double totalPayroll = 0.0;
		String dataInitial, datafinal, value, salary;
		if (emp.getPaymentDay().contains("mensal"))
			startDate = dateVerific.minusDays(day[1] - 1);
		else
			startDate = dateVerific.minusDays((day[0] * 7) - 1);

		dataInitial = startDate.getDayOfMonth() + "/" + startDate.getMonthValue() + "/"
				+ startDate.getYear();
		datafinal = dateVerific.getDayOfMonth() + "/" + dateVerific.getMonthValue()
				+ "/"
				+ dateVerific.getYear();

		// pega o valor das horas normais.

		value = CardController.getHoras(key, "normal", dataInitial,
				datafinal);

		salary = Conversor.converterInvertedCharacter(employee.getSalario());

		// soma os valores encontrados

		totalPayroll += Float.parseFloat(value) * Float.parseFloat(salary);

		// pega o valor das horas extras
		value = CardController.getHoras(key, "extra", dataInitial,
				datafinal);

		// adiciona o valor das horas extras

		totalPayroll += Float.parseFloat(value) * (Float.parseFloat(salary) * 1.5);

		return totalPayroll;
	}

	public double salaried(Employee emp, double dividendo) {
		String salary = Conversor.converterInvertedCharacter(emp.getSalario());

		Double salarioAmount = Double.parseDouble(salary);
		if (dividendo > 0)
			salarioAmount = Math.floor((salarioAmount * (dividendo / 52D)) * 2D * 100) / 100F;
		salarioAmount = ((int) (salarioAmount * 100)) / 100.0d;

		return salarioAmount;
	}

	public String getPayroll() {
		return payroll;
	}
}
