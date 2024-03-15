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