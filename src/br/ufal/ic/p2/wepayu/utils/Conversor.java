package br.ufal.ic.p2.wepayu.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import br.ufal.ic.p2.wepayu.Exception.DateInvalideException;

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
}
