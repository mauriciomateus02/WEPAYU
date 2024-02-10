package br.ufal.ic.p2.wepayu.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Map;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;
import br.ufal.ic.p2.wepayu.models.CartaoPontos;

public class uploadCartoes {

    final static String outputFile = "database/teste2.txt";

    public uploadCartoes() throws EmpregadoNaoExisteException, ExceptionCriarEmpregado {

        File file = new File(outputFile);
        BufferedWriter bf = null;

        try {

            // cria o buffer para salvar os dados no txt
            bf = new BufferedWriter(new FileWriter(file, true));

            for (Map.Entry<String, ArrayList<CartaoPontos>> entry : CartaoController.CartaoPontos.entrySet()) {
                // adiciona todas as informações dos empregados no txt
                bf.write(entry.getKey() + "-" + entry.getValue().toString());
                bf.newLine();
            }

            bf.flush();

        } catch (Exception e) {
            throw new EmpregadoNaoExisteException();
        } finally {

            try {
                // always close the writer
                bf.close();
            } catch (Exception e) {
                throw new ExceptionCriarEmpregado("Erro em salvar os dados");
            }
        }

    }
}
