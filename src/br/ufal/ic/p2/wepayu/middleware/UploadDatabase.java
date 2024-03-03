package br.ufal.ic.p2.wepayu.middleware;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;
import br.ufal.ic.p2.wepayu.utils.EnumType.getEnumDatabase;

public class UploadDatabase {

    private static String outputFile = null;

    public static <T> void uploadData(getEnumDatabase EnumDatabase, HashMap<String, T> map)
            throws EmpregadoNaoExisteException, ExceptionCriarEmpregado {

        switch (EnumDatabase) {
            case Employee:
                outputFile = "database/Employee.txt";
                break;
            case Unionized:
                outputFile = "database/Unionized.txt";
                break;
            case Payment:
                outputFile = "database/Payment.txt";
                break;
            default:
                break;
        }
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

}