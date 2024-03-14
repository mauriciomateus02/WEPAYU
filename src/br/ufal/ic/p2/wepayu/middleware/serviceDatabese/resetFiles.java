package br.ufal.ic.p2.wepayu.middleware.serviceDatabese;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;

public class resetFiles {
    public static void resetData(String file) throws FileNotFoundException {
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
