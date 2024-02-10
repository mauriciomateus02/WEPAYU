package br.ufal.ic.p2.wepayu.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.zip.DataFormatException;

import br.ufal.ic.p2.wepayu.Exception.DataInvalidaException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.controller.EmpregadoController;

public class validatorCartaoPontos {

    public static void validarCartao(String emp, String data, String hora)
            throws EmpregadoNaoExisteException, DataFormatException, DataInvalidaException, ExceptionGetEmpregado {

        // verifica se há algum empregado com o identificador passado
        if (EmpregadoController.Empregados.containsKey(emp)) {

            // como ele existe, é passado para a variavel tipo o tipo do empregado
            String tipo = EmpregadoController.Empregados.get(emp).getTipo();

            // se ele for horista ele continua as validações.
            if (tipo.equals("horista")) {
                // valida se a data está escrita certo.
                // tipo: 3 significa que é uma validação normal e dirá se a data é valida ou não
                validatorData(data, 3);
                // verifica se as horas foram passadas.
                validatorHoras(hora);
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

    public static void validarHorasTrabalhadas(String dataInicio, String dataFinal)
            throws DataFormatException, DataInvalidaException, EmpregadoNaoExisteException {

        validatorData(dataInicio, 1);
        validatorData(dataFinal, 2);

        LocalDate dateInicio = null, dateFim = null;

        // converte as datas para moder ser manipulada
        dateInicio = convertData(dataInicio, 1);
        dateFim = convertData(dataFinal, 2);

        // verifica se a data inicial é maior que a data final
        if (dateInicio.getDayOfMonth() > dateFim.getDayOfMonth()
                && dateInicio.getMonthValue() >= dateFim.getMonthValue()) {
            throw new DataInvalidaException("Data inicial nao pode ser posterior a data final.");
        } else if (dateInicio.getDayOfMonth() < dateFim.getDayOfMonth()
                && dateInicio.getMonthValue() > dateFim.getMonthValue()) {
            throw new DataInvalidaException("Data inicial nao pode ser posterior a data final.");
        }

    }

    private static void validatorData(String data, int tipo) throws DataFormatException, DataInvalidaException {
        String[] formato = { "d/M/yyyy", "dd/MM/yyyy", "d/MM/yyyy", "dd/M/yyyy" };
        int cont = 0;

        String[] str = data.split("/");

        if (data.isEmpty()) {
            // se a data vier vazia lança o erro
            throw new DataInvalidaException("Data nao pode ser nula.");
        }
        for (String fromatter : formato) {
            try {
                // System.out.println(fromatter);
                // define o formato da data
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(fromatter);
                // converte a data para o formato desejado
                LocalDate dataParsed = LocalDate.parse(data, dateFormatter);
                if (Integer.parseInt(str[0]) > dataParsed.lengthOfMonth()) {
                    if (tipo == 1)
                        throw new DataInvalidaException("Data inicial invalida.");
                    else if (tipo == 2)
                        throw new DataInvalidaException("Data final invalida.");
                    else
                        throw new DataInvalidaException("Data invalida.");
                }
                break;
            } catch (Exception e) {
                cont++;
            }
        }

        if (cont == 4 && tipo == 1) {
            // se ocorrer erro na conversão lança o erro data invalida.
            throw new DataInvalidaException("Data inicial invalida.");
        } else if (cont == 4 && tipo == 2) {
            // se ocorrer erro na conversão lança o erro data invalida.
            throw new DataInvalidaException("Data final invalida.");
        } else if (cont == 4 && tipo == 3) {
            // se ocorrer erro na conversão lança o erro data invalida.
            throw new DataInvalidaException("Data invalida.");
        }
    }

    private static void validatorHoras(String hora) throws DataInvalidaException {
        if (hora.isEmpty()) {
            // se a hora vier vazia lança o erro
            throw new DataInvalidaException("Horas não devem ser nulas");
        }
        try {
            // se a hora conter "," signfica que ela é um numero com casa decimal
            if (hora.contains(",")) {
                // é convertido o "," em "." e convertido para float
                hora = conversorCaracter.conversorInvert(hora);
                float horas = Float.parseFloat(hora);
                // se o valor for menor ou igual a 0
                if (horas <= 0) {
                    // lança o erro
                    throw new DataInvalidaException("Horas devem ser positivas.");
                }

            }
            // se não tiver "," é um numero sem casas decimais.
            else {
                // valor é convertido
                float horas = Float.parseFloat(hora);
                // analisado se é memnor ou igual a 0
                if (horas <= 0) {
                    // se sim, lança o erro.
                    throw new DataInvalidaException("Horas devem ser positivas.");
                }
            }
        } catch (Exception e) {
            // se não fpr possivel converter é porque foi passado letras, dessa forma lança
            // o erro.
            throw new DataInvalidaException("Horas devem ser positivas.");
        }
    }

    public static LocalDate convertData(String data, int tipo) throws DataInvalidaException {
        LocalDate date = null;
        String[] formato = { "d/M/yyyy", "dd/MM/yyyy", "d/MM/yyyy", "dd/M/yyyy" };
        int cont = 0;

        for (String fromatter : formato) {
            try {
                // System.out.println(fromatter);
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
            throw new DataInvalidaException("Data inicial invalida.");
        } else if (cont == 4 && tipo == 2) {
            // se ocorrer erro na conversão lança o erro data invalida.
            throw new DataInvalidaException("Data final invalida.");
        } else if (cont == 4 && tipo == 3) {
            throw new DataInvalidaException("Data invalida.");
        }

        return date;
    }

}