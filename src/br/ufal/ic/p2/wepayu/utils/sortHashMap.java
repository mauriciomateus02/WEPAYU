package br.ufal.ic.p2.wepayu.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.ufal.ic.p2.wepayu.models.Employee.Employee;

public class sortHashMap {

    public Map<String, Employee> ordenarPorNome(Map<String, Employee> map) {
        // Convertendo a HashMap para uma lista de entradas (Map.Entry)
        List<Map.Entry<String, Employee>> lista = new ArrayList<>(map.entrySet());

        // Ordenando a lista com base no nome do Employee
        Collections.sort(lista, new Comparator<Map.Entry<String, Employee>>() {
            @Override
            public int compare(Map.Entry<String, Employee> o1, Map.Entry<String, Employee> o2) {
                return o1.getValue().getNome().compareTo(o2.getValue().getNome());
            }
        });

        // Criando uma nova HashMap a partir da lista ordenada
        LinkedHashMap<String, Employee> mapaOrdenado = new LinkedHashMap<>();
        for (Map.Entry<String, Employee> entry : lista) {
            mapaOrdenado.put(entry.getKey(), entry.getValue());
        }

        return mapaOrdenado;
    }
}
