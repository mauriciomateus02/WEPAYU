package br.ufal.ic.p2.wepayu.middleware;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;

public class RemoveInDatabase {
    public static void removeEmpregado(String emp) throws EmpregadoNaoExisteException, FileNotFoundException {

        try {
            String file = "database/Employee.txt";
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
