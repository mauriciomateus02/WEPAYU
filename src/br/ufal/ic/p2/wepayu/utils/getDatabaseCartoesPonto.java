package br.ufal.ic.p2.wepayu.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

import br.ufal.ic.p2.wepayu.controller.CartaoController;

public class getDatabaseCartoesPonto {

    public static void getDatabaseCartoes() throws FileNotFoundException {

        CartaoController.CartaoPontos = new HashMap<>();

        try {

            String file = "database/teste2.txt";
            String linha;
            BufferedReader reader = new BufferedReader(new FileReader(file));

            while ((linha = reader.readLine()) != null) {

                // retira todos os caracteres problematicos
                linha = linha.replace("[", "");
                linha = linha.replace("]", "");
                linha = linha.replace(" ", "");

                // defini que a String será dividida apartir do "-"
                // dessa forma a string 0 é o id do empregado
                String index;
                String[] strs = linha.split("-");

                index = strs[0];

                String[] newlinha = strs[1].split(",");

                for (String str : newlinha) {

                    String stg[] = str.split(";");
                    CartaoController.lancaCartao(index, stg[0], stg[1]);
                }
            }

            reader.close();

        } catch (Exception e) {
            throw new FileNotFoundException();
        }
    }
}
