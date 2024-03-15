package br.ufal.ic.p2.wepayu.utils;

import java.time.DayOfWeek;
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

public class Payroll {

	public static String TotalPayroll(String date)
			throws DateInvalideException, ExceptionGetEmpregado, DataFormatException,
			EmpregadoNaoExisteException {

		try {

			Validator.validatorDate(date, getEnumActiveTurn.Default);

			LocalDate dateVerific = Conversor.converterDate(date, 3);
			LocalDate contraction = LocalDate.of(2005, 1, 1);

			float totalPayroll = 0;
			String salary = "";

			Long checkWeek = ChronoUnit.DAYS.between(contraction, dateVerific);

			for (Map.Entry<String, Employee> entry : EmployeeController.Empregados.entrySet()) {
				Employee emp = entry.getValue();

				int[] day = getPaymentEmployee(emp.getPaymentDay(), dateVerific);

				if (emp.getPaymentDay().contains("mensal") && checkEmployee(day[1], dateVerific)) {
					if (emp.getTipo().equals("assalariado")) {
						salary = Conversor.converterInvertedCharacter(emp.getSalario());

						Double salarioAmount = Double.parseDouble(salary);

						salarioAmount = ((int) (salarioAmount * 100)) / 100.0d;

						totalPayroll += salarioAmount;
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
						&& checkEmployee(checkWeek, day[0], day[1], dateVerific)) {
					if (emp.getTipo().equals("assalariado")) {
						salary = Conversor.converterInvertedCharacter(emp.getSalario());

						Double salarioAmount = Double.parseDouble(salary);
						double dividendo = (double) day[2];

						salarioAmount = Math.floor((salarioAmount * (dividendo / 52D)) * 2D * 100) / 100F;
						salarioAmount = ((int) (salarioAmount * 100)) / 100.0d;

						totalPayroll += salarioAmount;
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
			return String.format("%.2f", totalPayroll);
		} catch (Exception e) {
			throw e;
		}
	}

	private static int[] getPaymentEmployee(String pay, LocalDate date) {

		String[] paymentDay = pay.split(" ");
		int[] aux = new int[3];

		if (paymentDay[0].equals("mensal")) {

			aux[0] = date.lengthOfMonth() - 1;

			if (paymentDay[1].equals("$"))
				aux[1] = date.lengthOfMonth();
			else
				aux[1] = Integer.parseInt(paymentDay[1]);
			aux[2] = 24;
			return aux;
		} else {
			if (paymentDay.length == 2) {
				aux[0] = 1;
				aux[1] = Integer.parseInt(paymentDay[1]);
				aux[2] = 6;
				return aux;
			} else {
				aux[0] = Integer.parseInt(paymentDay[1]);
				aux[1] = Integer.parseInt(paymentDay[2]);
				aux[2] = Integer.parseInt(paymentDay[1]) * 6;

				return aux;
			}
		}
	}

	private static Boolean checkEmployee(Long check, int day, int dayWeek, LocalDate dateVerific) {

		if (day == 1 && (check % day == 0 && dateVerific.getDayOfWeek() == dayWeek(dayWeek))) {
			return true;
		} else if (day > 1 && ((check % ((day * 7) - dayWeek) == 0 && dateVerific.getDayOfWeek() == dayWeek(dayWeek))
				|| ((check + 1) % ((day * 7)) == 0 && dateVerific.getDayOfWeek() == dayWeek(dayWeek)))) {

			return true;
		}
		return false;
	}

	private static Boolean checkEmployee(int day, LocalDate dateVerific) {
		if (dateVerific.getDayOfMonth() == day) {

			return true;
		}
		return false;
	}

	private static DayOfWeek dayWeek(int day) {
		switch (day) {
			case 1:
				return DayOfWeek.MONDAY;
			case 2:
				return DayOfWeek.TUESDAY;
			case 3:
				return DayOfWeek.WEDNESDAY;
			case 4:
				return DayOfWeek.THURSDAY;
			case 5:
				return DayOfWeek.FRIDAY;
			case 6:
				return DayOfWeek.SATURDAY;
			case 7:
				return DayOfWeek.SUNDAY;
			default:
				return null;

		}
	}

	private static double commissoned(String key, Employee emp, LocalDate dateVerific, int[] day)
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

	private static double hourly(String key, Employee emp, LocalDate dateVerific, int[] day)
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
}
