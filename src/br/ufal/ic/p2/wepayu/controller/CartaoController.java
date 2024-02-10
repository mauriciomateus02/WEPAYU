package br.ufal.ic.p2.wepayu.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.zip.DataFormatException;

import br.ufal.ic.p2.wepayu.Exception.DataInvalidaException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.models.CartaoPontos;
import br.ufal.ic.p2.wepayu.utils.conversorCaracter;
import br.ufal.ic.p2.wepayu.utils.validatorCartaoPontos;

public class CartaoController {

    public static HashMap<String, ArrayList<CartaoPontos>> CartaoPontos;

    public static void lancaCartao(String emp, String data, String hora)
            throws EmpregadoNaoExisteException, DataFormatException, DataInvalidaException, ExceptionGetEmpregado {
        if (emp.isEmpty() || emp.equals("")) {
            throw new ExceptionGetEmpregado("Identificacao do empregado nao pode ser nula.");
        }
        // valida os campos retornados para poder encontrar exceções
        validatorCartaoPontos.validarCartao(emp, data, hora);

        // verifica a existenca se o empregado já tem suas horas cadastradas.
        if (CartaoPontos.containsKey(emp)) {
            hora = conversorCaracter.conversorInvert(hora);
            CartaoPontos card = new CartaoPontos(data, Float.parseFloat(hora));
            CartaoPontos.get(emp).add(card);
        }
        // caso o empregado não tenha horas cadastradas é realizado o cadastro das
        // horas.
        else {
            hora = conversorCaracter.conversorInvert(hora);
            CartaoPontos card = new CartaoPontos(data, Float.parseFloat(hora));
            ArrayList<CartaoPontos> cards = new ArrayList<>();
            cards.add(card);
            CartaoPontos.put(emp, cards);
        }
    }

    public static String getHoras(String emp, String tipoHora, String dataInicio, String dataFinal)
            throws ExceptionGetEmpregado, DataFormatException, DataInvalidaException, EmpregadoNaoExisteException {
        if (emp.isEmpty() || emp.equals("")) {
            throw new ExceptionGetEmpregado("Identificacao do empregado nao pode ser nula.");
        }
        // verifica a existencia de erros
        validatorCartaoPontos.validarHorasTrabalhadas(dataInicio, dataFinal);

        if (EmpregadoController.Empregados.containsKey(emp)) {

            String tipo = EmpregadoController.Empregados.get(emp).getTipo();

            // se o empregado existir na base de dados mas não foi cadastrado nada
            // retornará 0;
            if (tipo.equals("horista") && !CartaoPontos.containsKey(emp)) {
                return Integer.toString(0);
            }

            else if (tipo.equals("horista") && CartaoPontos.containsKey(emp)) {

                float value = 0;
                LocalDate fim = null, date = null;
                ArrayList<CartaoPontos> cards;

                // aloca em cards os valores em horas do empregado
                cards = CartaoPontos.get(emp);

                // converte a string em data para ser analisada.
                fim = validatorCartaoPontos.convertData(dataFinal, 2);

                // converte a data inicial
                String[] str = dataInicio.split("/");

                for (CartaoPontos card : cards) {

                    // converte a string em data para ser analisada.
                    date = validatorCartaoPontos.convertData(card.getData(), 3);

                    // verifica se a data que está sendo verificada é menor que a ultima analisada.
                    if (date.getDayOfMonth() >= Integer.parseInt(str[0]) && date.getDayOfMonth() < fim.getDayOfMonth()
                            && date.getMonthValue() <= fim.getMonthValue()
                            && tipoHora.equals("normal")) {

                        // caso exista horas maior que 8 ele só guarda 8 o resto será contabilizado na
                        // hora extra
                        if (Float.parseFloat(card.gethoras()) > 8) {

                            value += 8;
                        } else {
                            // caso não exista ele guarda mesmo assim para contabilizar as horas trabalhadas
                            value += Float.parseFloat(card.gethoras());
                        }
                    }

                    else if (date.getDayOfMonth() < fim.getDayOfMonth() && date.getMonthValue() <= fim.getMonthValue()
                            && tipoHora.equals("extra")) {
                        float temp = Float.parseFloat(card.gethoras());
                        if (temp > 8) {
                            // caso exista hora maior que 8 ele guada a diferença
                            value += temp - 8;
                        } else {
                            // caso exista hora menor ele retira das horas extra
                            value -= (8 - temp);
                        }
                    }
                }
                // após a contagem das horas o valor é convertido
                String valueConvertido = conversorCaracter.conversor(Float.toString(value));
                // se o valor conter ,0 significa que é um valor inteiro e será dividido
                if (valueConvertido.contains(",0")) {
                    // divide na virgula
                    String sttrs[] = valueConvertido.split(",");
                    // retorna
                    return sttrs[0];
                } else {
                    return valueConvertido;
                }

            } else {
                throw new ExceptionGetEmpregado("Empregado nao eh horista.");
            }

        } else {
            throw new EmpregadoNaoExisteException();
        }
    }

}
