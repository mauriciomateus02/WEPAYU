package br.ufal.ic.p2.wepayu.middleware.serviceDatabase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileNotFoundException;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.models.StrategyDB.DeleteEntityDB;

public class removeLine implements DeleteEntityDB {

    @Override
    public void connect(String file, String key)
            throws EmpregadoNaoExisteException, FileNotFoundException {

        try {
            String linha;
            BufferedReader bf = new BufferedReader(new FileReader(file));
            StringBuilder sBuilder = new StringBuilder();

            // vai ler o arquivo teste.txt para remover o Empregado desejado.
            while ((linha = bf.readLine()) != null) {

                String obj[] = linha.split(";");

                if (!obj[0].equals(key)) {
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
