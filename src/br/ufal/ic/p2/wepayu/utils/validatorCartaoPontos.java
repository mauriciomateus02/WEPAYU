package br.ufal.ic.p2.wepayu.utils;

import br.ufal.ic.p2.wepayu.Exception.DateInvalideException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.controller.EmployeeController;
import br.ufal.ic.p2.wepayu.utils.EnumType.getEnumActiveTurn;

public class ValidatorCartaoPontos {

    public static void validarCartao(String emp, String data, String hora)
            throws EmpregadoNaoExisteException, DateInvalideException, ExceptionGetEmpregado {

        // verifica se há algum empregado com o identificador passado
        if (EmployeeController.Empregados.containsKey(emp)) {

            // como ele existe, é passado para a variavel tipo o tipo do empregado
            String tipo = EmployeeController.Empregados.get(emp).getTipo();

            // se ele for horista ele continua as validações.
            if (tipo.equals("horista")) {
                // valida se a data está escrita certo.
                // getEnumActiveTurn.Default significa que é uma validação normal e dirá se a
                // data é valida ou não
                Validator.validatorDate(data, getEnumActiveTurn.Default);
                // verifica se as horas foram passadas.
                Validator.validatorHours(hora);

            } else {
                // lança o erro porque o empregado não é do tipo horista
                throw new ExceptionGetEmpregado("Empregado nao eh horista.");
            }

        }
        // se o empregado não existir na base de dados de empregados é lançado a
        // exception.
        else {
            throw new EmpregadoNaoExisteException();
        }
    }

}