package br.ufal.ic.p2.wepayu.middleware;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.controller.EmployeeController;
import br.ufal.ic.p2.wepayu.utils.Conversor;

public class getDatabaseEmployee {

    public static void getDatabaseEmpregado() throws FileNotFoundException {

        EmployeeController.Empregados = new HashMap<>();

        try {
            String file = "database/teste.txt";
            String linha;
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((linha = reader.readLine()) != null) {
                // pega o objeto empregado salvo e divide em duas partes.
                // o que estiver do outro lado da seta -> será o array que
                // ele tem associoado

                String[] employee = linha.split("->");
                String[] obj = employee[0].split(";");
                String[] list;

                if (employee[1].length() > 2) {

                    employee[1] = Conversor.converterArray(employee[1]);
                    list = employee[1].split(",");
                    if (obj[3].equals("horista")) {

                        obj[5] = Conversor.converterInvertedCharacter(obj[5]);
                        setDataEmployee.setEmployee(obj[0], obj[1], obj[2], obj[3], obj[4], Float.parseFloat(obj[5]),
                                list);
                    }

                    else if (obj[3].equals("comissionado")) {

                        obj[5] = Conversor.converterInvertedCharacter(obj[5]);
                        obj[6] = Conversor.converterInvertedCharacter(obj[6]);

                        setDataEmployee.setEmployee(obj[0], obj[1], obj[2], obj[3], obj[4], Float.parseFloat(obj[5]),
                                Float.parseFloat(obj[6]), list);

                    }

                } else {

                    if (obj[3].equals("assalariado")) {

                        obj[5] = Conversor.converterInvertedCharacter(obj[5]);
                        setDataEmployee.setEmployee(obj[0], obj[1], obj[2], obj[3], obj[4], Float.parseFloat(obj[5]));
                    }

                    else if (obj[3].equals("comissionado")) {

                        obj[5] = Conversor.converterInvertedCharacter(obj[5]);
                        obj[6] = Conversor.converterInvertedCharacter(obj[6]);

                        setDataEmployee.setEmployee(obj[0], obj[1], obj[2], obj[3], obj[4], Float.parseFloat(obj[5]),
                                Float.parseFloat(obj[6]));

                    }

                    else if (obj[3].equals("horista")) {

                        obj[5] = Conversor.converterInvertedCharacter(obj[5]);
                        setDataEmployee.setEmployee(obj[0], obj[1], obj[2], obj[3], obj[4], Float.parseFloat(obj[5]));
                    }
                }
            }
            System.out.println();
            reader.close();

            EmployeeController.index = EmployeeController.index + 1;

        } catch (Exception e) {
            throw new FileNotFoundException();
        }

    }

    public static void removeEmpregado(String emp) throws EmpregadoNaoExisteException, FileNotFoundException {

        try {
            String file = "database/teste.txt";
            String linha;
            BufferedReader bf = new BufferedReader(new FileReader(file));
            StringBuilder sBuilder = new StringBuilder();

            // vai ler o arquivo teste.txt para remover o Empregado desejado.
            while ((linha = bf.readLine()) != null) {

                String obj[] = linha.split(";");

                if (!obj[0].equals(emp)) {
                    // analisa e aqueles que não forem o que queremos excluir guarda no sbuilder
                    sBuilder.append(linha);
                    sBuilder.append(System.lineSeparator());
                }
            }

            bf.close();
            // cria um buffer para adicionar no arquivo os dados que não podem excluir
            BufferedWriter escrever = new BufferedWriter(new FileWriter(file));
            // escreve os dados no arquivo
            escrever.write(sBuilder.toString());
            escrever.close();

        } catch (Exception e) {
            throw new FileNotFoundException();
        }
    }

}
