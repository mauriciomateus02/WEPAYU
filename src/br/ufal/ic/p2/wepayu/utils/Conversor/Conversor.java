package br.ufal.ic.p2.wepayu.utils.Conversor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.ufal.ic.p2.wepayu.Exception.DateInvalideException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.controller.employee.EmployeeController;
import br.ufal.ic.p2.wepayu.models.Employee.Employee;
import br.ufal.ic.p2.wepayu.models.Employee.Commissioned.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.Employee.Hourly.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.models.Employee.Salaried.EmpregadoAssalariado;

public class Conversor {

    public static String converterCharacter(String str) {
        str = str.replace('.', ',');
        return str;
    }

    public static String converterInvertedCharacter(String str) {
        str = str.replace(',', '.');
        return str;
    }

    public static LocalDate converterDate(String data, int tipo) throws DateInvalideException {

        LocalDate date = null;
        String[] formato = { "d/M/yyyy", "dd/MM/yyyy", "d/MM/yyyy", "dd/M/yyyy" };
        int cont = 0;

        for (String fromatter : formato) {
            try {
                // define o formato da data
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(fromatter);
                // converte a data para o formato desejado
                date = LocalDate.parse(data, dateFormatter);

                break;

            } catch (Exception e) {
                cont++;
            }
        }
        if (cont == 4 && tipo == 1) {
            // se ocorrer erro na conversão lança o erro data invalida.
            throw new DateInvalideException("Data inicial invalida.");
        } else if (cont == 4 && tipo == 2) {
            // se ocorrer erro na conversão lança o erro data invalida.
            throw new DateInvalideException("Data final invalida.");
        } else if (cont == 4 && tipo == 3) {
            throw new DateInvalideException("Data invalida.");
        }

        return date;
    }

    public static String converterArray(String str) {
        str = str.replace("[", "");
        str = str.replace("]", "");
        str = str.replace(" ", "");

        return str;
    }

    static public void converterEmployee(String employeeID, Employee employee, String type)
            throws NumberFormatException, ExceptionCriarEmpregado, ExceptionGetEmpregado {

        String salary;
        Employee NewEmployee;

        switch (type) {
            case "assalariado":
                // verifica se o salario possue casas decimais separada e converte caso
                // a divisão seja pela virgula.
                salary = (employee.getSalario().contains(","))
                        ? Conversor.converterInvertedCharacter(employee.getSalario())
                        : employee.getSalario();
                // cria um nono empregado e atualiza o registro
                NewEmployee = new EmpregadoAssalariado(employee.getNome(),
                        employee.getEndereco(), type, Float.parseFloat(salary));

                break;
            case "horista":
                // verifica se o salario possue casas decimais separada e converte caso
                // a divisão seja pela virgula.
                salary = (employee.getSalario().contains(","))
                        ? Conversor.converterInvertedCharacter(employee.getSalario())
                        : employee.getSalario();
                // cria um nono empregado e atualiza o registro
                NewEmployee = new EmpregadoAssalariado(employee.getNome(),
                        employee.getEndereco(), type, Float.parseFloat(salary));
                break;
            default:
                throw new ExceptionGetEmpregado("Tipo invalido.");
        }
        EmployeeController.Empregados.put(employeeID, NewEmployee);
    }

    static public void converterEmployee(String employeeID, Employee employee, String type, String amount)
            throws NumberFormatException, ExceptionCriarEmpregado, ExceptionGetEmpregado {

        float amountAux;
        String salary;
        Employee NewEmployee;

        switch (type) {

            case "comissionado":
                // verifica se o salario possue casas decimais separada e converte caso
                // a divisão seja pela virgula.
                salary = (employee.getSalario().contains(","))
                        ? Conversor.converterInvertedCharacter(employee.getSalario())
                        : employee.getSalario();
                // verifica a existencia de virgula na comissão e troca pelo ponto
                // para poder ocorrer a conversão para float
                amount = (employee.getSalario().contains(","))
                        ? Conversor.converterInvertedCharacter(amount)
                        : amount;
                // garante que nao ah letra na comissao
                amountAux = conversorNumeric("comissao", amount);

                if (amountAux <= 0) {
                    throw new ExceptionCriarEmpregado("Comissao deve ser nao-negativa.");
                }
                // cria um nono empregado e atualiza o registro
                NewEmployee = new EmpregadoComissionado(employee.getNome(),
                        employee.getEndereco(), type, Float.parseFloat(salary), amountAux);
                break;

            case "horista":
                // verifica se o salario possue casas decimais separada e converte caso
                // a divisão seja pela virgula.
                amount = (employee.getSalario().contains(","))
                        ? Conversor.converterInvertedCharacter(amount)
                        : amount;

                // garante que não há letras no valor passado
                amountAux = conversorNumeric("salario", amount);

                if (amountAux <= 0) {
                    throw new ExceptionCriarEmpregado("Salario deve ser nao-negativa.");
                }

                // cria um nono empregado e atualiza o registro
                NewEmployee = new EmpregadoHorista(employee.getNome(),
                        employee.getEndereco(), type, amountAux);
                break;
            default:
                throw new ExceptionGetEmpregado("Tipo invalido.");
        }

        EmployeeController.Empregados.put(employeeID, NewEmployee);
    }

    public static float conversorNumeric(String attribute, String value) throws ExceptionGetEmpregado {
        try {
            return Float.parseFloat(value);
        } catch (Exception e) {

            switch (attribute) {
                case "salario":
                    throw new ExceptionGetEmpregado("Salario deve ser numerico.");
                case "comissao":
                    throw new ExceptionGetEmpregado("Comissao deve ser numerica.");
                case "taxaSindical":
                    throw new ExceptionGetEmpregado("Taxa sindical deve ser numerica.");
                default:
                    throw new ExceptionGetEmpregado("Valor deve ser numerica.");
            }

        }
    }

    public static String padRightWithSpaces(String input, int length) {
        StringBuilder builder = new StringBuilder(input);
        while (builder.length() < length) {
            builder.append(' ');
        }
        return builder.toString();
    }

    public static String padLeftWithSpaces(String input, int length) {
        StringBuilder builder = new StringBuilder();
        while (builder.length() + input.length() < length) {
            builder.append(' '); // Adiciona espaços em branco antes da string original
        }
        return builder.toString() + input;
    }
}
