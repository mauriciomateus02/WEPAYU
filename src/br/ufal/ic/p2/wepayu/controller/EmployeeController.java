package br.ufal.ic.p2.wepayu.controller;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.Exception.ExceptionRemoveEmpregado;
import br.ufal.ic.p2.wepayu.middleware.getDatabaseEmployee;
import br.ufal.ic.p2.wepayu.models.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.models.Employee;
import br.ufal.ic.p2.wepayu.models.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.models.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.utils.Conversor;
import br.ufal.ic.p2.wepayu.utils.ValidatorEmployee;

public class EmployeeController {

    public static int index = 0;
    public static HashMap<String, Employee> Empregados;

    public static String criarEmpregado(String nome, String endereco, String tipo, String salario)
            throws ExceptionCriarEmpregado {

        // transforma o valor do salario para poder ser convertido ex.: chega 20,00
        // transforma em 20.00
        salario = Conversor.converterInvertedCharacter(salario);

        // valida os campos para que não ocorra erros
        ValidatorEmployee.validatorEmpregados(nome, endereco, tipo, salario);

        if (tipo.equals("horista")) {
            EmpregadoHorista empregado = new EmpregadoHorista(nome, endereco, tipo, Float.parseFloat(salario));
            return EmployeeController.AdicionarEmpregado(empregado);

        }

        else if (tipo.equals("assalariado")) {
            EmpregadoAssalariado empregadoAssalariado = new EmpregadoAssalariado(nome, endereco, tipo,
                    Float.parseFloat(salario));
            return EmployeeController.AdicionarEmpregado(empregadoAssalariado);
        }

        else {
            throw new ExceptionCriarEmpregado("Tipo invalido.");
        }
    }

    public static String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao)
            throws ExceptionCriarEmpregado {

        // transforma o valor do salario para poder ser convertido ex.: chega 20,00
        // transforma em 20.00
        salario = Conversor.converterInvertedCharacter(salario);

        // transforma a comissão em valor que pode ser convertido ex.: chega 20,00
        // transforma em 20.00
        comissao = Conversor.converterInvertedCharacter(comissao);

        // valida os campos para que não ocorra erros
        ValidatorEmployee.validatorEmpregados(nome, endereco, tipo, salario, comissao);

        if (tipo.equals("comissionado")) {
            // cria o empregado camossionado e salva ele no hashamp
            EmpregadoComissionado emprCom = new EmpregadoComissionado(nome, endereco, tipo, Float.parseFloat(salario),
                    Float.parseFloat(comissao));
            return EmployeeController.AdicionarEmpregado(emprCom);
        }

        else {
            throw new ExceptionCriarEmpregado("Tipo invalido.");
        }

    }

    public static String AdicionarEmpregado(Employee emp) {
        String id = Integer.toString(index);

        Empregados.put(id, emp);
        index++;
        return id;

    }

    public static void backupDatabase(String id, Employee emp) {
        Empregados.put(id, emp);
    }

    public static String getEmpregadoPorNome(String nome, int id) throws ExceptionGetEmpregado {

        for (Map.Entry<String, Employee> emp : EmployeeController.Empregados.entrySet()) {
            if (emp.getValue().getNome().equals(nome) && id == 0) {
                return emp.getKey();
            } else if (emp.getValue().getNome().equals(nome) && id > 0) {
                id--;
            }
        }
        throw new ExceptionGetEmpregado("Nao ha empregado com esse nome.");
    }

    public static String getAtributo(String emp, String atributo)

            throws EmpregadoNaoExisteException, ExceptionGetEmpregado {

        if (emp.isEmpty() || emp.equals("")) {
            throw new ExceptionGetEmpregado("Identificacao do empregado nao pode ser nula.");
        }

        else if (EmployeeController.Empregados.containsKey(emp) == false) {
            throw new EmpregadoNaoExisteException();
        } else if (EmployeeController.Empregados.get(emp) == null) {
            throw new EmpregadoNaoExisteException();
        }
        String str;
        Employee empr;
        empr = EmployeeController.Empregados.get(emp);

        // exibe o atributo buscado pelo usuário do empregado desejado
        switch (atributo) {
            case "nome":
                return empr.getNome();

            case "endereco":
                return empr.getEndereco();

            case "tipo":
                return empr.getTipo();

            case "sindicalizado":
                return empr.getSindicalizado().toString();

            case "salario":
                str = empr.getSalario();
                return Conversor.converterCharacter(str);

            case "comissao":
                EmpregadoComissionado empre = (EmpregadoComissionado) empr;
                str = empre.getComissao();
                return Conversor.converterCharacter(str);

            default:
                throw new ExceptionGetEmpregado("Atributo nao existe.");
        }
    }

    public static void removerEmpregado(String emp)
            throws EmpregadoNaoExisteException, ExceptionRemoveEmpregado, FileNotFoundException {
        // verifica se o id é nulo
        if (emp.isEmpty()) {
            throw new ExceptionRemoveEmpregado();
        } else if (Empregados.get(emp) == null) {
            throw new EmpregadoNaoExisteException();
        } else {
            getDatabaseEmployee.removeEmpregado(emp);
            Empregados.remove(emp);
        }

    }

}
