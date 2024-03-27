package br.ufal.ic.p2.wepayu.models.Payroll;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.DataFormatException;

import br.ufal.ic.p2.wepayu.Exception.*;
import br.ufal.ic.p2.wepayu.controller.employee.*;
import br.ufal.ic.p2.wepayu.models.DTO.Payroll.CommissionedDTO;
import br.ufal.ic.p2.wepayu.models.DTO.Payroll.HourlyDTO;
import br.ufal.ic.p2.wepayu.models.DTO.Payroll.SalariedDTO;
import br.ufal.ic.p2.wepayu.models.Employee.Employee;
import br.ufal.ic.p2.wepayu.models.Employee.Commissioned.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.Employee.Hourly.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.models.Employee.Salaried.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.utils.GetTemplate;
import br.ufal.ic.p2.wepayu.utils.sortHashMap;
import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;
import br.ufal.ic.p2.wepayu.utils.Validator.ValidatorDatePayroll;

public class Payroll {

    public void generatePayroll(String outfile, String dateFinal)
            throws DateInvalideException, FileNotFoundException, ExceptionGetEmpregado, DataFormatException,
            EmpregadoNaoExisteException {
        sortHashMap sHashMap = new sortHashMap();

        HashMap<String, Employee> empregadosOriginal = new HashMap<>(EmployeeController.Empregados);
        Map<String, Employee> empregadosOrdenados = sHashMap.ordenarPorNome(empregadosOriginal);

        TotalPayroll totalPayroll = new TotalPayroll(dateFinal);
        GetTemplate gTemplate = new GetTemplate();

        File file = new File(outfile);
        BufferedWriter bf = null;

        LocalDate deadline = Conversor.converterDate(dateFinal, 3);

        PayrollHourly pHourly = new PayrollHourly();
        PayrollSalaried pSalaried = new PayrollSalaried();
        PayrollCommissioned pCommissioned = new PayrollCommissioned();

        try {
            bf = new BufferedWriter(new FileWriter(file, false));

            bf.write("FOLHA DE PAGAMENTO DO DIA " + deadline.toString());
            bf.newLine();

            gTemplate.getTemplate("templates/hourly.txt", bf);

            for (Map.Entry<String, Employee> emp : empregadosOrdenados.entrySet()) {

                int[] day = ValidatorDatePayroll.getPaymentEmployee(emp.getValue().getPaymentDay(), deadline);

                if (emp.getValue().getTipo().equals("horista") && checkWeek(deadline, day)) {

                    EmpregadoHorista empHourly = (EmpregadoHorista) emp.getValue();

                    bf.write(pHourly.employeeHourly(emp.getKey(), empHourly, totalPayroll, deadline));
                    bf.newLine();

                }

            }

            bf.newLine();
            bf.write(pHourly.totalEmployeesHourly("TOTAL HORISTAS"));
            bf.newLine();
            bf.newLine();

            gTemplate.getTemplate("templates/salaried.txt", bf);

            for (Map.Entry<String, Employee> emp : empregadosOrdenados.entrySet()) {

                int[] day = ValidatorDatePayroll.getPaymentEmployee(emp.getValue().getPaymentDay(), deadline);

                if (emp.getValue().getTipo().equals("assalariado")
                        && ValidatorDatePayroll.getPaymentMonth(day[1], deadline)) {

                    EmpregadoAssalariado empSalaried = (EmpregadoAssalariado) emp.getValue();

                    bf.write(pSalaried.employeeSalaried(emp.getKey(), empSalaried, totalPayroll, deadline));
                    bf.newLine();

                }

            }

            bf.newLine();
            bf.write(pSalaried.totalEmployeeSalaried("TOTAL ASSALARIADOS"));
            bf.newLine();

            gTemplate.getTemplate("templates/commissioned.txt", bf);

            for (Map.Entry<String, Employee> emp : empregadosOrdenados.entrySet()) {

                int[] day = ValidatorDatePayroll.getPaymentEmployee(emp.getValue().getPaymentDay(), deadline);

                if (emp.getValue().getTipo().equals("comissionado")
                        && checkWeek(deadline, day)) {

                    EmpregadoComissionado empSalaried = (EmpregadoComissionado) emp.getValue();

                    bf.write(
                            pCommissioned.employeeCommissioned(emp.getKey(), empSalaried, totalPayroll, deadline, day));
                    bf.newLine();

                }

            }
            bf.newLine();
            bf.write(pCommissioned.totalEmployeeCommissioned("TOTAL COMISSIONADOS"));

            bf.newLine();
            bf.newLine();

            bf.write("TOTAL FOLHA: " + totalPayroll.getPayroll());
            bf.flush();

            CommissionedDTO.resetData();
            SalariedDTO.resetData();
            HourlyDTO.resetData();

        } catch (Exception e) {
            throw new FileNotFoundException();
        } finally {

            try {
                // always close the writer
                bf.close();
            } catch (Exception e) {
                throw new FileNotFoundException("Erro em salvar os dados");
            }
        }

    }

    private static boolean checkWeek(LocalDate dateVerific, int[] day) {

        LocalDate contraction = LocalDate.of(2005, 1, 1);
        Long check = ChronoUnit.WEEKS.between(contraction, dateVerific);

        return ValidatorDatePayroll.getPaymentWeek(check + 1, day[0], day[1], dateVerific);
    }
}
