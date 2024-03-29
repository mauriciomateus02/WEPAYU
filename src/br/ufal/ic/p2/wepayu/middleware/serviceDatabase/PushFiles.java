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
import br.ufal.ic.p2.wepayu.models.StrategyDB.CreateEntityDB;

public class PushFiles implements CreateEntityDB {

    @Override
    public <K, T> void connect(String outputFile, HashMap<K, T> map)
            throws EmpregadoNaoExisteException, ExceptionCriarEmpregado {

        File file = new File(outputFile);
        BufferedWriter bf = null;

        try {

            // cria o buffer para salvar os dados no txt
            bf = new BufferedWriter(new FileWriter(file, true));

            for (Map.Entry<K, T> entry : map.entrySet()) {
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

    @Override
    public void connect(String file, ArrayList<String> list) throws ExceptionCreatePaymentDay {

        File outfile = new File(file);
        BufferedWriter bf = null;

        try {

            // cria o buffer para salvar os dados no txt
            bf = new BufferedWriter(new FileWriter(outfile, true));

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