package br.ufal.ic.p2.wepayu.middleware.serviceDatabase;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionCreatePaymentDay;
import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;

public class PushFiles {

    public static <T> void uploadData(String outputFile, HashMap<String, T> map)
            throws EmpregadoNaoExisteException, ExceptionCriarEmpregado {

        File file = new File(outputFile);
        BufferedWriter bf = null;

        try {

            // cria o buffer para salvar os dados no txt
            bf = new BufferedWriter(new FileWriter(file, true));

            for (Map.Entry<String, T> entry : map.entrySet()) {
                // adiciona todas as informações dos empregados no txt
                bf.write(entry.getKey() + ";" + entry.getValue().toString());
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

    public static void upload(ArrayList<String> list, String outFile)
            throws ExceptionCreatePaymentDay {

        File file = new File(outFile);
        BufferedWriter bf = null;

        try {

            // cria o buffer para salvar os dados no txt
            bf = new BufferedWriter(new FileWriter(file, true));

            for (String item : list) {
                // adiciona todas as informações dos empregados no txt
                bf.write(item);
                bf.newLine();
            }

            bf.flush();

        } catch (Exception e) {
            throw new ExceptionCreatePaymentDay("Erro ao salvar agenda");
        } finally {

            try {

                // always close the writer
                bf.close();
            } catch (Exception e) {
                throw new ExceptionCreatePaymentDay("Erro em salvar os dados");
            }
        }
    }
}