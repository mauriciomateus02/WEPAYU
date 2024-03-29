package br.ufal.ic.p2.wepayu.middleware.serviceDatabase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import br.ufal.ic.p2.wepayu.models.StrategyDB.DBConnection;

public class GetFiles implements DBConnection {

    @Override
    public void connect(String file) throws FileNotFoundException {

        ValidatorOfEntity validatorOfEntity = new ValidatorOfEntity();

        try {

            String linha;
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((linha = reader.readLine()) != null) {

                validatorOfEntity.setEntities(linha);

            }
            // verifica se a requisição de pegar os dados de do tipo employee
            reader.close();

        } catch (Exception e) {
            throw new FileNotFoundException();
        }
    }
}