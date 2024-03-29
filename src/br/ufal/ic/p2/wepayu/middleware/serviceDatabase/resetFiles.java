package br.ufal.ic.p2.wepayu.middleware.serviceDatabase;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import br.ufal.ic.p2.wepayu.models.StrategyDB.DBConnection;

public class resetFiles implements DBConnection {

    @Override
    public void connect(String file) throws FileNotFoundException {

        try {

            BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
            // limpa o arquivo
            writer.write("");
            // finaliza a alteração
            writer.close();

        } catch (Exception e) {
            throw new FileNotFoundException();
        }
    }
}
