package br.ufal.ic.p2.wepayu.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.ufal.ic.p2.wepayu.Exception.DateInvalideException;
import br.ufal.ic.p2.wepayu.utils.EnumType.getEnumActiveTurn;

public class Validator {

    public static void validatorDate(String data, getEnumActiveTurn tipo) throws DateInvalideException {

        String[] formato = { "d/M/yyyy", "dd/MM/yyyy", "d/MM/yyyy", "dd/M/yyyy" };
        int cont = 0;

        String[] str = data.split("/");

        if (data.isEmpty()) {
            // se a data vier vazia lança o erro
            throw new DateInvalideException("Data nao pode ser nula.");
        }
        for (String fromatter : formato) {
            try {
                // System.out.println(fromatter);
                // define o formato da data
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(fromatter);
                // converte a data para o formato desejado
                LocalDate dataParsed = LocalDate.parse(data, dateFormatter);
                if (Integer.parseInt(str[0]) > dataParsed.lengthOfMonth()) {

                    switch (tipo) {
                        case InitialDate:
                            throw new DateInvalideException("Data inicial invalida.");
                        case DeadLine:
                            throw new DateInvalideException("Data final invalida.");
                        case Default:
                            throw new DateInvalideException("Data invalida.");
                        default:
                            break;
                    }
                }
                break;
            } catch (Exception e) {
                cont++;
            }
        }

        switch (tipo) {
            case InitialDate:
                if (cont == 4)
                    // se ocorrer erro na conversão lança o erro data invalida.
                    throw new DateInvalideException("Data inicial invalida.");
                break;
            case DeadLine:
                if (cont == 4)
                    // se ocorrer erro na conversão lança o erro data invalida.
                    throw new DateInvalideException("Data final invalida.");
                break;
            case Default:
                if (cont == 4)
                    // se ocorrer erro na conversão lança o erro data invalida.
                    throw new DateInvalideException("Data invalida.");
                break;
            default:
                break;
        }

    }

    public static void validatorHours(String hours) throws DateInvalideException {

        try {

            if (hours.isEmpty()) {
                // se a hora vier vazia lança o erro
                new DateInvalideException("Horas não devem ser nulas");
            }
            // se a hora conter "," signfica que ela é um numero com casa decimal
            if (hours.contains(",")) {
                // é convertido o "," em "." e convertido para float
                hours = Conversor.converterInvertedCharacter(hours);
                float horas = Float.parseFloat(hours);
                // se o valor for menor ou igual a 0
                if (horas <= 0) {
                    // lança o erro
                    new DateInvalideException("Horas devem ser positivas.");
                }

            }
            // se não tiver "," é um numero sem casas decimais.
            else {
                // valor é convertido
                float horas = Float.parseFloat(hours);
                // analisado se é memnor ou igual a 0
                if (horas <= 0) {
                    // se sim, lança o erro.
                    new DateInvalideException("Horas devem ser positivas.");
                }
            }

        } catch (Exception e) {
            throw e;
        }
    }

    public static Boolean validatorRangeDate(String[] dateInitial, LocalDate dateVerific, LocalDate deadline) {

        int VerificDay = Integer.parseInt(dateInitial[0]);
        int VerificMonth = Integer.parseInt(dateInitial[1]);
        int VerificYear = Integer.parseInt(dateInitial[2]);

        if ((dateVerific.getDayOfMonth() >= VerificDay
                && dateVerific.getMonthValue() >= VerificMonth
                && dateVerific.getYear() >= VerificYear)
                && dateVerific.getDayOfYear() < deadline.getDayOfYear()
                && dateVerific.getYear() <= deadline.getYear()) {

            return true;
        }
        return false;
    }

    public static void validateSearchDate(String dateInitial, String deadline) throws DateInvalideException {
        Validator.validatorDate(dateInitial, getEnumActiveTurn.InitialDate);
        Validator.validatorDate(deadline, getEnumActiveTurn.DeadLine);

        LocalDate startDate = null, dateFinal = null;

        // converte as datas para moder ser manipulada
        startDate = Conversor.converterDate(dateInitial, 1);
        dateFinal = Conversor.converterDate(deadline, 2);

        // verifica se a data inicial é maior que a data final
        if (startDate.getDayOfMonth() > dateFinal.getDayOfMonth()
                && startDate.getMonthValue() >= dateFinal.getMonthValue()) {
            throw new DateInvalideException("Data inicial nao pode ser posterior a data final.");
        } else if (startDate.getDayOfMonth() < dateFinal.getDayOfMonth()
                && startDate.getMonthValue() > dateFinal.getMonthValue()) {
            throw new DateInvalideException("Data inicial nao pode ser posterior a data final.");
        }
    }

}
