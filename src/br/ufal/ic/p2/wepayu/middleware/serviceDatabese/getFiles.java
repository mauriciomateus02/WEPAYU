package br.ufal.ic.p2.wepayu.middleware.serviceDatabese;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class getFiles {

    public static void getEntites(String file)
            throws FileNotFoundException {

        try {

            String linha;
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((linha = reader.readLine()) != null) {
                ValidatorOfEntity.setEntities(linha);
            }
            // verifica se a requisição de pegar os dados de do tipo employee
            reader.close();

        } catch (Exception e) {
            throw new FileNotFoundException();
        }
    }
}

// String[] database = linha.split("->");
// String[] objs = database[0].split(";");
// String[] list;

// if (database[1].length() > 2) {

// // retira tudo que pode causar problema na conversão
// database[1] = Conversor.converterArray(database[1]);
// list = database[1].split(",");

// if (objs[0].contains("s")) {
// // como o identificador começa com s sabemos que é do tipo sindicalizado
// DataRetriever.setUnionized(objs[1], objs[2], objs[3], list);

// } else if (objs[3].equals("assalariado") || objs[3].equals("horista")) {
// // como o tamanho é 5 significa que ele pode ser assalariado ou horista
// objs[5] = Conversor.converterInvertedCharacter(objs[5]);
// DataRetriever.setEmployee(objs[0], objs[1], objs[2], objs[3], objs[4],
// Float.parseFloat(objs[5]),
// list);
// } else if (objs[3].equals("comissionado")) {
// // como o tamanho é 6 significa que é comissionado.
// objs[5] = Conversor.converterInvertedCharacter(objs[5]);
// objs[6] = Conversor.converterInvertedCharacter(objs[6]);

// DataRetriever.setEmployee(objs[0], objs[1], objs[2], objs[3], objs[4],
// Float.parseFloat(objs[5]),
// Float.parseFloat(objs[6]), list);
// }

// }

// else if (objs.length == 3) {

// if (objs[2].equals("emMaos") || objs[2].equals("correios")) {
// DataRetriever.setPayment(objs[1], objs[2]);
// }
// }

// else if (objs.length == 6) {
// if (objs[2].equals("banco")) {
// DataRetriever.setPayment(objs[1], objs[2], objs[3], objs[4], objs[5]);
// }
// }

// else {

// if (objs[0].contains("s")) {

// // como é usado no employee cada objs represneta uma informação da class
// DataRetriever.setUnionized(objs[1], objs[2], objs[3]);

// }
// if (objs[3].equals("assalariado") || objs[3].equals("horista")) {
// // converte a virgula em ponto ex.: 20,00 em 20.00
// if (objs[5].contains(","))
// objs[5] = Conversor.converterInvertedCharacter(objs[5]);

// DataRetriever.setEmployee(objs[0], objs[1], objs[2], objs[3], objs[4],
// Float.parseFloat(objs[5]));

// } else if (objs[3].equals("comissionado")) {
// // converte a comissão e o salario para o tipo float
// objs[5] = Conversor.converterInvertedCharacter(objs[5]);
// objs[6] = Conversor.converterInvertedCharacter(objs[6]);

// DataRetriever.setEmployee(objs[0], objs[1], objs[2], objs[3], objs[4],
// Float.parseFloat(objs[5]),
// Float.parseFloat(objs[6]));
// }
// }