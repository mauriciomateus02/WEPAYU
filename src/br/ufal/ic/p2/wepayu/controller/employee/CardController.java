package br.ufal.ic.p2.wepayu.controller.employee;

import java.time.LocalDate;
import java.util.zip.DataFormatException;

import br.ufal.ic.p2.wepayu.Exception.DateInvalideException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.models.Employee.Hourly.CartaoPontos;
import br.ufal.ic.p2.wepayu.models.Employee.Hourly.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.utils.Validator.Validator;
import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;
import br.ufal.ic.p2.wepayu.utils.Validator.ValidatorCartaoPontos;

public class CardController {

    public static void lancaCartao(String emp, String data, String hora)
            throws EmpregadoNaoExisteException, DataFormatException, DateInvalideException, ExceptionGetEmpregado {

        if (emp.isEmpty() || emp.equals("")) {
            throw new ExceptionGetEmpregado("Identificacao do empregado nao pode ser nula.");
        }
        // valida os campos retornados para poder encontrar exceções
        ValidatorCartaoPontos.validarCartao(emp, data, hora);

        EmpregadoHorista empregado = (EmpregadoHorista) EmployeeController.Empregados.get(emp);

        if (hora.contains(",")) {
            hora = Conversor.converterInvertedCharacter(hora);
            CartaoPontos card = new CartaoPontos(data, Float.parseFloat(hora));
            empregado.addCartaoPontos(card);
        } else {
            CartaoPontos card = new CartaoPontos(data, Float.parseFloat(hora));
            empregado.addCartaoPontos(card);
        }

        EmployeeController.Empregados.put(emp, empregado);
    }

    public static String getHoras(String emp, String tipoHora, String dataInicio, String dataFinal)
            throws ExceptionGetEmpregado, DataFormatException, DateInvalideException, EmpregadoNaoExisteException {

        if (emp.isEmpty()) {
            throw new ExceptionGetEmpregado("Identificacao do empregado nao pode ser nula.");
        }
        // verifica a existencia de erros

        Validator.validateSearchDate(dataInicio, dataFinal);

        if (EmployeeController.Empregados.containsKey(emp)) {

            String tipo = EmployeeController.Empregados.get(emp).getTipo();

            // se o empregado existir na base de dados mas não foi cadastrado nada
            // retornará 0;
            if (tipo.equals("horista")) {

                EmpregadoHorista empregado = (EmpregadoHorista) EmployeeController.Empregados.get(emp);

                if (empregado.getCartaoPontos().size() == 0) {
                    return Integer.toString(0);
                }

                else {

                    float value = 0, extra = 0;
                    LocalDate deadline = null, dateVerific = null, startDate = null;
                    String valueConvertido;

                    // converte a string em data para ser analisada.
                    deadline = Conversor.converterDate(dataFinal, 2);
                    startDate = Conversor.converterDate(dataInicio, 1);

                    for (CartaoPontos card : empregado.getCartaoPontos()) {

                        // converte a string em data para ser analisada.

                        dateVerific = Conversor.converterDate(card.getData(), 3);

                        // condição diz que se a data verificada for maior ou igual a data de inicio
                        // e a data verificada estiver no range de data inicio e data final
                        if (dateVerific.getDayOfYear() >= startDate.getDayOfYear()
                                && dateVerific.getYear() == startDate.getYear()
                                && dateVerific.getDayOfYear() < deadline.getDayOfYear()
                                && dateVerific.getYear() == deadline.getYear()) {

                            if (card.gethoras() >= 8) {
                                value += 8;
                                extra += (card.gethoras() - 8);
                            } else {
                                value += card.gethoras();
                            }
                        }

                    }

                    // verifica se existe hora extra, caso contrario

                    extra = (extra < 0) ? 0 : extra;

                    // após a contagem das horas o valor é convertido
                    if (tipoHora.equals("normal")) {

                        valueConvertido = Conversor.converterCharacter(Float.toString(value));
                    } else {
                        valueConvertido = Conversor.converterCharacter(Float.toString(extra));
                    }
                    // se o valor conter ,0 significa que é um valor inteiro e será dividido
                    if (valueConvertido.contains(",0")) {
                        // divide na virgula
                        String sttrs[] = valueConvertido.split(",");
                        // retorna
                        return sttrs[0];
                    } else {
                        return valueConvertido;
                    }
                }

            } else {
                throw new ExceptionGetEmpregado("Empregado nao eh horista.");
            }

        } else {
            throw new EmpregadoNaoExisteException();
        }
    }

}
