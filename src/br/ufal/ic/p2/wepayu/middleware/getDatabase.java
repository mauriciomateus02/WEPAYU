package br.ufal.ic.p2.wepayu.middleware;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import br.ufal.ic.p2.wepayu.controller.EmployeeController;
import br.ufal.ic.p2.wepayu.controller.UnionServiceController;
import br.ufal.ic.p2.wepayu.models.Unionized;
import br.ufal.ic.p2.wepayu.utils.Conversor;
import br.ufal.ic.p2.wepayu.utils.EnumType.getEnumDatabase;

public class getDatabase {

    private static String file;;

    public static <T> void getDatabaseEntites(getEnumDatabase type, HashMap<String, T> map)
            throws FileNotFoundException {

        switch (type) {
            case Employee:
                EmployeeController.Empregados = new HashMap<>();
                file = "database/Employee.txt";
                break;
            case Unionized:
                UnionServiceController.employeesUnionzed = new HashMap<>();
                file = "database/Unionized.txt";
                break;
            default:
                file = null;
                break;
        }

        try {

            String linha;
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((linha = reader.readLine()) != null) {

                String[] database = linha.split("->");
                String[] objs = database[0].split(";");
                String[] list;

                if (database[1].length() > 2) {

                    // retira tudo que pode causar problema na conversão
                    database[1] = Conversor.converterArray(database[1]);
                    list = database[1].split(",");

                    if (objs[0].contains("s")) {
                        // como o identificador começa com s sabemos que é do tipo sindicalizado
                        DataRetriever.setUnionized(objs[1], objs[2], objs[3], list);

                    } else if (objs[3].equals("assalariado") || objs[3].equals("horista")) {
                        // como o tamanho é 5 significa que ele pode ser assalariado ou horista
                        objs[5] = Conversor.converterInvertedCharacter(objs[5]);
                        DataRetriever.setEmployee(objs[0], objs[1], objs[2], objs[3], objs[4],
                                Float.parseFloat(objs[5]),
                                list);
                    } else if (objs[3].equals("comissionado")) {
                        // como o tamanho é 6 significa que é comissionado.
                        objs[5] = Conversor.converterInvertedCharacter(objs[5]);
                        objs[6] = Conversor.converterInvertedCharacter(objs[6]);

                        DataRetriever.setEmployee(objs[0], objs[1], objs[2], objs[3], objs[4],
                                Float.parseFloat(objs[5]),
                                Float.parseFloat(objs[6]), list);
                    }

                } else {

                    if (objs[0].contains("s")) {
                        // como é usado no employee cada objs represneta uma informação da class
                        Unionized union = new Unionized(objs[1], objs[2], Float.parseFloat(objs[3]));
                        UnionServiceController.addUnionized(union, objs[0]);

                    } else if (objs[3].equals("assalariado") || objs[3].equals("horista")) {
                        // converte a virgula em ponto ex.: 20,00 em 20.00
                        if (objs[5].contains(","))
                            objs[5] = Conversor.converterInvertedCharacter(objs[5]);

                        DataRetriever.setEmployee(objs[0], objs[1], objs[2], objs[3], objs[4],
                                Float.parseFloat(objs[5]));

                    }

                    else if (objs[3].equals("comissionado")) {

                        // converte a comissão e o salario para o tipo float
                        objs[5] = Conversor.converterInvertedCharacter(objs[5]);
                        objs[6] = Conversor.converterInvertedCharacter(objs[6]);

                        DataRetriever.setEmployee(objs[0], objs[1], objs[2], objs[3], objs[4],
                                Float.parseFloat(objs[5]),
                                Float.parseFloat(objs[6]));

                    }
                }
            }
            // verifica se a requisição de pegar os dados de do tipo employee
            if (type == getEnumDatabase.Employee) {
                EmployeeController.index += 1;
            }
            reader.close();

        } catch (Exception e) {
            throw new FileNotFoundException();
        }
    }
}
