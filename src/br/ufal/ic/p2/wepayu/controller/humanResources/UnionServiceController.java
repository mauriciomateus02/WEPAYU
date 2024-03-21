package br.ufal.ic.p2.wepayu.controller.humanResources;

import java.time.LocalDate;
import java.util.HashMap;

import br.ufal.ic.p2.wepayu.Exception.DateInvalideException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.controller.employee.ControllerEmpregado;
import br.ufal.ic.p2.wepayu.models.Unionized.ServiceFee;
import br.ufal.ic.p2.wepayu.models.Unionized.Unionized;
import br.ufal.ic.p2.wepayu.utils.Validator.Validator;
import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;
import br.ufal.ic.p2.wepayu.utils.EnumType.getEnumActiveTurn;

public class UnionServiceController {

    public static HashMap<String, Unionized> employeesUnionzed;

    public static void createEmployeeUnionzed(String employee, String unionID, String taxaSindicato)
            throws ExceptionGetEmpregado {
        // realiza a verificação dos campos
        if (employee.isEmpty()) {
            throw new ExceptionGetEmpregado("Identificacao do empregado nao pode ser nula.");
        }
        if (unionID.isEmpty()) {
            throw new ExceptionGetEmpregado("Identificacao do sindicato nao pode ser nula.");
        }
        if (taxaSindicato.isEmpty()) {
            throw new ExceptionGetEmpregado("Taxa sindical nao pode ser nula.");
        }
        // verifica se a txa contém virgula e se tiver troca pelo ponto
        // para poder converter o valor em float
        if (taxaSindicato.contains(","))
            taxaSindicato = Conversor.converterInvertedCharacter(taxaSindicato);
        float valortaxaSindicato = Conversor.conversorNumeric("taxaSindical", taxaSindicato);

        if (valortaxaSindicato <= 0) {
            throw new ExceptionGetEmpregado("Taxa sindical deve ser nao-negativa.");
        }
        if (employeesUnionzed.containsKey(unionID)) {
            throw new ExceptionGetEmpregado("Ha outro empregado com esta identificacao de sindicato");
        }

        Unionized union = new Unionized(unionID, employee, valortaxaSindicato);

        employeesUnionzed.put(unionID, union);

        // associa o empregado ao sindicato
        ControllerEmpregado.Empregados.get(employee).setUnionized(union);

    }

    public static boolean verificateEmployeeUnionzed(String unionID) {
        return (employeesUnionzed.containsKey(unionID)) ? true : false;
    }

    public static void createServiceFee(String unionID, String date, String valor)
            throws DateInvalideException, ExceptionGetEmpregado {

        if (unionID.isEmpty())
            throw new ExceptionGetEmpregado("Identificacao do empregado nao pode ser nula.");
        else if (!verificateEmployeeUnionzed(unionID))
            throw new ExceptionGetEmpregado("Membro nao existe.");
        // verifica se a data passada é válida, default significa que a data será
        // avaliada de forma normal
        Validator.validatorDate(date, getEnumActiveTurn.Default);

        float valortaxaSindicato;
        // converte o valor em float para realizar a verificação do valor passado.
        valortaxaSindicato = (valor.contains(",")) ? Float.parseFloat(Conversor.converterInvertedCharacter(valor))
                : Float.parseFloat(valor);
        // verifica se o valor é menor que zero e se for lança erro.
        if (valortaxaSindicato <= 0) {
            throw new DateInvalideException("Valor deve ser positivo.");
        }
        // cria nova taxa de serviço
        ServiceFee service = new ServiceFee(date, valortaxaSindicato);

        employeesUnionzed.get(unionID).addServiceFee(service);

    }

    public static String getServiceFee(String employeeID, String startDate, String prazo)
            throws ExceptionGetEmpregado, DateInvalideException, EmpregadoNaoExisteException {

        if (employeeID.isEmpty()) {
            throw new ExceptionGetEmpregado("Identificacao do empregado nao pode ser nula.");
        }
        if (!ControllerEmpregado.Empregados.get(employeeID).getSindicalizado()) {
            throw new ExceptionGetEmpregado("Empregado nao eh sindicalizado.");
        }

        // verifica a data passada
        Validator.validatorDate(startDate, getEnumActiveTurn.InitialDate);
        Validator.validatorDate(prazo, getEnumActiveTurn.prazo);
        Validator.validateSearchDate(startDate, prazo);

        // cria variaveis do tipo data para fazer a validação
        LocalDate dateFinal = null, dateVerific = null, dataInicial = null;
        // variavel para auxiliar na conversão dos valores
        String temp;
        float valor = 0;

        // converte a data que foi enviada e verifica se está correta
        dateFinal = Conversor.converterDate(prazo, 2);
        // divide a data inicial para analisar o range
        dataInicial = Conversor.converterDate(startDate, 1);

        // pega o empregado sindicalizado
        Unionized union = ControllerEmpregado.Empregados.get(employeeID).getUnionized();

        // verifica as taxas cobradas do empregado
        for (ServiceFee service : union.getServiceFee()) {
            dateVerific = Conversor.converterDate(service.getDate(), 3);

            if (Validator.validatorRangeDate(dataInicial, dateVerific, dateFinal)) {
                if (service.getValue().contains(",")) {
                    // converte o valor ex.: 20,00 em 20.00
                    temp = Conversor.converterInvertedCharacter(service.getValue());
                    // transforma e soma o valor buscado
                    valor += Float.parseFloat(temp);
                } else {
                    // transforma e soma o valor buscado
                    valor += Float.parseFloat(service.getValue());
                }
            }
        }

        String valor_as_string = String.format("%.2f", valor);
        return Conversor.converterCharacter(valor_as_string);
        // Valor formatado em duas casas decimais e retornado como string com vírgula
    }

    public static void addUnionized(String IDsindicalizado, Unionized union) {
        ControllerEmpregado.Empregados.get(union.getEmployeeID()).setUnionized(union);
        employeesUnionzed.put(IDsindicalizado, union);
    }

    public static void setUnionized(String employeeId, String unionID, String taxaSindicato) throws ExceptionGetEmpregado {
        if (employeeId.isEmpty()) {
            throw new ExceptionGetEmpregado("Identificacao do empregado nao pode ser nula.");
        }
        if (unionID.isEmpty()) {
            throw new ExceptionGetEmpregado("Identificacao do sindicato nao pode ser nula.");
        }
        if (taxaSindicato.isEmpty()) {
            throw new ExceptionGetEmpregado("Taxa sindical nao pode ser nula.");
        }

        if (taxaSindicato.contains(","))
            taxaSindicato = Conversor.converterInvertedCharacter(taxaSindicato);

        float auxFee = Conversor.conversorNumeric("taxaSindical", taxaSindicato);

        Unionized unionized;

        // verifica se o empregado já é sindicalizado
        if (ControllerEmpregado.Empregados.get(employeeId).getSindicalizado())
            unionized = employeesUnionzed.get(unionID);
        // caso não seja sendicalizado é criado e atribuido
        else
            unionized = new Unionized(unionID, employeeId, null);

        if (auxFee <= 0) {
            throw new ExceptionGetEmpregado("Taxa sindical deve ser nao-negativa.");
        }
        // atualiza o valor da taxa sindical
        unionized.settaxaSindicato(auxFee);

        // atualiza o empregado
        ControllerEmpregado.Empregados.get(employeeId).setUnionized(unionized);

        // salva a alteração no hashmap
        employeesUnionzed.put(unionID, unionized);

    }
}
