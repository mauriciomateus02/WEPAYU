package br.ufal.ic.p2.wepayu.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class GetTemplate {
    public void getTemplate(String template, BufferedWriter bf) throws FileNotFoundException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(template));
            String linha;
            while ((linha = reader.readLine()) != null) {
                bf.write(linha);
                bf.newLine();
            }
        } catch (FileNotFoundException e) {
            throw e; // Se ocorrer FileNotFoundException, re-lança a exceção
        } catch (IOException e) {
            // Se ocorrer IOException, imprime o erro e fecha o BufferedWriter
            e.printStackTrace();
        } finally {
            // Fecha o BufferedReader no final, independentemente de exceções
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
