package br.ufal.ic.p2.wepayu.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import br.ufal.ic.p2.wepayu.Exception.DateInvalideException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.models.ServiceFee;
import br.ufal.ic.p2.wepayu.models.Unionized;
import br.ufal.ic.p2.wepayu.utils.Conversor;
import br.ufal.ic.p2.wepayu.utils.Validator;
import br.ufal.ic.p2.wepayu.utils.EnumType.getEnumActiveTurn;

public class UnionServiceController {

    public static HashMap<String, Unionized> employeesUnionzed;

    public static void createEmployeeUnionzed(String employee, String unionID, String unionfee)
            throws ExceptionGetEmpregado {

        if (employee.isEmpty()) {
            throw new ExceptionGetEmpregado("Identificacao do membro nao pode ser nula.");
        } else if (employeesUnionzed.containsKey(unionID)) {
            throw new ExceptionGetEmpregado("Ha outro empregado com esta identificacao de sindicato");
        }
        float valueUnionFee;
        // verifica se a txa contém virgula e se tiver troca pelo ponto
        // para poder converter o valor em float
        valueUnionFee = (unionfee.contains(",")) ? Float.parseFloat(Conversor.converterInvertedCharacter(unionfee))
                : Float.parseFloat(unionfee);
        Unionized union = new Unionized(unionID, employee, valueUnionFee);

        employeesUnionzed.put(unionID, union);
    }

    public static boolean verificateEmployeeUnionzed(String unionID) {
        return (employeesUnionzed.containsKey(unionID)) ? true : false;
    }

    public static void createServiceFee(String unionID, String date, String value)
            throws DateInvalideException, ExceptionGetEmpregado {

        if (unionID.isEmpty())
            throw new ExceptionGetEmpregado("Identificacao do membro nao pode ser nula.");
        else if (!verificateEmployeeUnionzed(unionID))
            throw new ExceptionGetEmpregado("Membro nao existe.");
        // verifica se a data passada é válida, default significa que a data será
        // avaliada de forma normal
        Validator.validatorDate(date, getEnumActiveTurn.Default);

        float valueUnionFee;
        // converte o valor em float para realizar a verificação do valor passado.
        valueUnionFee = (value.contains(",")) ? Float.parseFloat(Conversor.converterInvertedCharacter(value))
                : Float.parseFloat(value);
        // verifica se o valor é menor que zero e se for lança erro.
        if (valueUnionFee <= 0) {
            throw new DateInvalideException("Valor deve ser positivo.");
        }
        // cria nova taxa de serviço
        ServiceFee service = new ServiceFee(date, valueUnionFee);

        employeesUnionzed.get(unionID).addServiceFee(service);
    }

    public static String getServiceFee(String employeeID, String startDate, String deadline)
            throws ExceptionGetEmpregado, DateInvalideException, EmpregadoNaoExisteException {

        if (employeeID.isEmpty()) {
            throw new ExceptionGetEmpregado("Identificacao do membro nao pode ser nula.");
        }
        if (!EmployeeController.Empregados.get(employeeID).getSindicalizado()) {
            throw new ExceptionGetEmpregado("Empregado nao eh sindicalizado.");
        }

        // verifica a data passada
        Validator.validatorDate(startDate, getEnumActiveTurn.InitialDate);
        Validator.validatorDate(deadline, getEnumActiveTurn.DeadLine);
        Validator.validateSearchDate(startDate, deadline);

        // cria variaveis do tipo data para fazer a validação
        LocalDate dateFinal = null, dateVerific = null;
        // variavel para auxiliar na conversão dos valores
        String temp;
        float value = 0;

        String index = null;
        // converte a data que foi enviada e verifica se está correta
        dateFinal = Conversor.converterDate(deadline, 2);
        // divide a data inicial para analisar o range
        String[] dateInitial = startDate.split("/");

        // encontra o usuario do employee passado
        for (Map.Entry<String, Unionized> entry : employeesUnionzed.entrySet()) {
            if (entry.getValue().getEmployeeID().equals(employeeID)) {
                index = entry.getKey();
                break;
            }

        }
        if (index == null) {
            throw new EmpregadoNaoExisteException();
        }
        // pega o empregado sindicalizado
        Unionized union = employeesUnionzed.get(index);

        // verifica as taxas cobradas do empregado
        for (ServiceFee service : union.getServiceFee()) {
            dateVerific = Conversor.converterDate(service.getDate(), 3);

            if (Validator.validatorRangeDate(dateInitial, dateVerific, dateFinal)) {
                if (service.getValue().contains(",")) {
                    // converte o valor ex.: 20,00 em 20.00
                    temp = Conversor.converterInvertedCharacter(service.getValue());
                    // transforma e soma o valor buscado
                    value += Float.parseFloat(temp);
                } else {
                    // transforma e soma o valor buscado
                    value += Float.parseFloat(service.getValue());
                }
            }
        }

        return Conversor.converterCharacter(String.format("%.2f", value));
    }

    public static void addUnionized(Unionized union, String unionizedID) {
        employeesUnionzed.put(unionizedID, union);
    }
}
