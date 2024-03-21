package br.ufal.ic.p2.wepayu.utils.Validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.ufal.ic.p2.wepayu.Exception.DateInvalideException;
import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;
import br.ufal.ic.p2.wepayu.utils.EnumType.getEnumActiveTurn;

public class Validator {

    public static void validatorDate(String data, getEnumActiveTurn tipo) throws DateInvalideException {

        String[] formato = { "d/M/yyyy", "dd/MM/yyyy", "d/MM/yyyy", "dd/M/yyyy" };
        int cont = 0;
        String[] str;
        String day;
        LocalDate dataParsed = null;

        if (data.isEmpty()) {
            // se a data vier vazia lança o erro
            throw new DateInvalideException("Data nao pode ser nula.");
        }

        str = data.split("/");
        day = str[0];

        for (String fromatter : formato) {
            try {

                // define o formato da data
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(fromatter);
                // converte a data para o formato desejado
                dataParsed = LocalDate.parse(data, dateFormatter);

                if (Integer.parseInt(day) > dataParsed.lengthOfMonth()) {

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
                } else
                    break;
            } catch (Exception e) {
                cont++;
            }
        }

        if (cont == 4) {

            switch (tipo) {
                case InitialDate:
                    // se ocorrer erro na conversão lança o erro data invalida.
                    throw new DateInvalideException("Data inicial invalida.");
                case DeadLine:
                    // se ocorrer erro na conversão lança o erro data invalida.
                    throw new DateInvalideException("Data final invalida.");

                case Default:
                    // se ocorrer erro na conversão lança o erro data invalida.
                    throw new DateInvalideException("Data invalida.");
                default:
                    break;
            }
        }

    }

    public static void validatorHours(String hours) throws DateInvalideException {

        try {
            if (hours.isEmpty()) {
                // se a hora vier vazia lança o erro
                throw new DateInvalideException("Horas não devem ser nulas");
            }
            // se a hora conter "," signfica que ela é um numero com casa decimal
            if (hours.contains(",")) {
                // é convertido o "," em "." e convertido para float
                hours = Conversor.converterInvertedCharacter(hours);
                float horas = Float.parseFloat(hours);
                // se o valor for menor ou igual a 0
                if (horas <= 0) {
                    // lança o erro
                    throw new DateInvalideException("Horas devem ser positivas.");
                }

            }
            // se não tiver "," é um numero sem casas decimais.
            else {
                // valor é convertido
                float horas = Float.parseFloat(hours);
                // analisado se é memnor ou igual a 0
                if (horas <= 0) {

                    // se sim, lança o erro.
                    throw new DateInvalideException("Horas devem ser positivas.");
                }
            }

        } catch (Exception e) {
            throw e;
        }
    }

    public static Boolean validatorRangeDate(LocalDate startDate, LocalDate dateVerific, LocalDate deadline) {

        if (dateVerific.getDayOfYear() >= startDate.getDayOfYear()
                && dateVerific.getYear() == startDate.getYear()
                && dateVerific.getDayOfYear() < deadline.getDayOfYear()
                && dateVerific.getYear() == deadline.getYear()) {

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
        if (startDate.getDayOfYear() > dateFinal.getDayOfYear()
                && startDate.getYear() >= dateFinal.getYear()) {
            throw new DateInvalideException("Data inicial nao pode ser posterior a data final.");
        } else if (startDate.getDayOfYear() < dateFinal.getDayOfYear()
                && startDate.getMonthValue() > dateFinal.getMonthValue()) {
            throw new DateInvalideException("Data inicial nao pode ser posterior a data final.");
        }
    }

}
