package br.ufal.ic.p2.wepayu.utils.Validator;

import java.time.DayOfWeek;
import java.time.LocalDate;

import br.ufal.ic.p2.wepayu.Exception.DateInvalideException;
import br.ufal.ic.p2.wepayu.models.Employee.Employee;

public class ValidatorDatePayroll {

    public static boolean getPaymentMonth(int day, LocalDate dateVerific) {
        if (dateVerific.getDayOfMonth() == day) {

            return true;
        }
        return false;
    }

    public static boolean getPaymentWeek(Long check, int week, int dayWeek, LocalDate dateVerific) {
        if (week == 1 && (check % week == 0 && dateVerific.getDayOfWeek() == dayWeek(dayWeek))) {
            return true;
        } else if (week > 1 && ((check % ((week)) == 0 && dateVerific.getDayOfWeek() == dayWeek(dayWeek))
                || ((check) % ((week)) == 0 && dateVerific.getDayOfWeek() == dayWeek(dayWeek)))) {

            return true;
        }
        return false;
    }

    public static int[] getPaymentEmployee(String pay, LocalDate date) {

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

    public static String generateStartDate(LocalDate deadline, Employee emp) throws DateInvalideException {

        int[] day = ValidatorDatePayroll.getPaymentEmployee(emp.getPaymentDay(), deadline);

        LocalDate startDate = deadline.minusDays((day[2] + (day[0] - 1)));
        String auxDate = startDate.toString();
        String[] dataInitial = auxDate.split("-");

        return dataInitial[2] + "/" + dataInitial[1] + "/" + dataInitial[0];

    }

    public static String generateDeadline(LocalDate deadline) throws DateInvalideException {

        String auxDate = deadline.toString();
        String[] dataFinal = auxDate.split("-");

        return dataFinal[2] + "/" + dataFinal[1] + "/" + dataFinal[0];

    }
}
