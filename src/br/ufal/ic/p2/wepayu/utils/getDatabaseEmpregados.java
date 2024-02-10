package br.ufal.ic.p2.wepayu.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.controller.EmpregadoController;
import br.ufal.ic.p2.wepayu.models.EmpreadoHorista;
import br.ufal.ic.p2.wepayu.models.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.models.EmpregadoComissionado;

public class getDatabaseEmpregados {

    public static void getDatabaseEmpregado() throws FileNotFoundException {

        EmpregadoController.Empregados = new HashMap<>();

        try {
            String file = "database/teste.txt";
            String linha;
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((linha = reader.readLine()) != null) {

                String[] obj = linha.split(";");

                if (obj[3].equals("horista")) {
                    // na posição 5 é o salario, dessa forma é trocado a virgula pelo ponto
                    obj[5] = obj[5].replace(",", ".");

                    EmpreadoHorista emp = new EmpreadoHorista(obj[1], obj[2], obj[3], Float.parseFloat(obj[5]));
                    // altera o tipo de sindicalizado conforme o que está salvo no txt
                    emp.setSindicalizado(Boolean.parseBoolean(obj[4]));

                    // Adiciona no HashMap o empregado guardado no txt
                    EmpregadoController.backupDatabase(obj[0], emp);
                }

                else if (obj[3].equals("assalariado")) {
                    // na posição 5 é o salario, dessa forma é trocado a virgula pelo ponto
                    obj[5] = obj[5].replace(",", ".");
                    // Cria o empregado com base nas informações salvas no txt
                    EmpregadoAssalariado emp = new EmpregadoAssalariado(obj[1], obj[2], obj[3],
                            Float.parseFloat(obj[5]));
                    // altera o tipo de sindicalizado conforme o que está salvo no txt
                    emp.setSindicalizado(Boolean.parseBoolean(obj[4]));
                    // Adiciona no HashMap o empregado guardado no txt
                    EmpregadoController.backupDatabase(obj[0], emp);
                }

                else if (obj[3].equals("comissionado")) {
                    // na posição 5 é o salario, dessa forma é trocado a virgula pelo ponto
                    obj[5] = obj[5].replace(",", ".");
                    // na posição 6 é o comissao, dessa forma é trocado a virgula pelo ponto
                    obj[6] = obj[6].replace(",", ".");

                    // Cria o empregado com base nas informações salvas no txt
                    EmpregadoComissionado emp = new EmpregadoComissionado(obj[1], obj[2], obj[3],
                            Float.parseFloat(obj[5]), Float.parseFloat(obj[6]));
                    // altera o tipo de sindicalizado conforme o que está salvo no txt
                    emp.setSindicalizado(Boolean.parseBoolean(obj[4]));

                    // Adiciona no HashMap o empregado guardado no txt
                    EmpregadoController.backupDatabase(obj[0], emp);

                }
                EmpregadoController.index = Integer.parseInt(obj[0]);
            }
            System.out.println();
            reader.close();

            EmpregadoController.index = EmpregadoController.index + 1;

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
