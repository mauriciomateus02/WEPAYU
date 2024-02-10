package br.ufal.ic.p2.wepayu.controller;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.Exception.ExceptionRemoveEmpregado;
import br.ufal.ic.p2.wepayu.models.EmpreadoHorista;
import br.ufal.ic.p2.wepayu.models.Empregado;
import br.ufal.ic.p2.wepayu.models.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.models.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.utils.conversorCaracter;
import br.ufal.ic.p2.wepayu.utils.getDatabaseEmpregados;
import br.ufal.ic.p2.wepayu.utils.validatorEmpregado;

public class EmpregadoController {

    public static int index = 0;
    public static HashMap<String, Empregado> Empregados;

    public static String AdicionarEmpregado(Empregado emp) {
        String id = Integer.toString(index);

        Empregados.put(id, emp);
        index++;
        return id;

    }

    public static void backupDatabase(String index, Empregado emp) {
        Empregados.put(index, emp);
    }

    public static String getEmpregadoPorNome(String nome, int indice) throws ExceptionGetEmpregado {

        for (Map.Entry<String, Empregado> emp : EmpregadoController.Empregados.entrySet()) {
            if (emp.getValue().getNome().equals(nome) && indice == 0) {
                return emp.getKey();
            } else if (emp.getValue().getNome().equals(nome) && indice > 0) {
                indice--;
            }
        }
        throw new ExceptionGetEmpregado("Nao ha empregado com esse nome.");
    }

    public static String getAtributo(String emp, String atributo)

            throws EmpregadoNaoExisteException, ExceptionGetEmpregado {

        if (emp.isEmpty() || emp.equals("")) {
            throw new ExceptionGetEmpregado("Identificacao do empregado nao pode ser nula.");
        }

        else if (EmpregadoController.Empregados.containsKey(emp) == false) {
            throw new EmpregadoNaoExisteException();
        } else if (EmpregadoController.Empregados.get(emp) == null) {
            throw new EmpregadoNaoExisteException();
        }
        String str;
        Empregado empr;
        empr = EmpregadoController.Empregados.get(emp);

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
                return conversorCaracter.conversor(str);

            case "comissao":
                EmpregadoComissionado empre = (EmpregadoComissionado) empr;
                str = empre.getComissao();
                return conversorCaracter.conversor(str);

            default:
                throw new ExceptionGetEmpregado("Atributo nao existe.");
        }
    }

    public static String criarEmpregado(String nome, String endereco, String tipo, String salario)
            throws ExceptionCriarEmpregado {

        // transforma o valor do salario para poder ser convertido ex.: chega 20,00
        // transforma em 20.00
        salario = conversorCaracter.conversorInvert(salario);

        // valida os campos para que não ocorra erros
        validatorEmpregado.validatorEmpregados(nome, endereco, tipo, salario);

        if (tipo.equals("horista")) {
            EmpreadoHorista empregado = new EmpreadoHorista(nome, endereco, tipo, Float.parseFloat(salario));
            return EmpregadoController.AdicionarEmpregado(empregado);

        }

        else if (tipo.equals("assalariado")) {
            EmpregadoAssalariado empregadoAssalariado = new EmpregadoAssalariado(nome, endereco, tipo,
                    Float.parseFloat(salario));
            return EmpregadoController.AdicionarEmpregado(empregadoAssalariado);
        }

        else {
            throw new ExceptionCriarEmpregado("Tipo invalido.");
        }
    }

    public static String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao)
            throws ExceptionCriarEmpregado {

        // transforma o valor do salario para poder ser convertido ex.: chega 20,00
        // transforma em 20.00
        salario = conversorCaracter.conversorInvert(salario);

        // transforma a comissão em valor que pode ser convertido
        comissao = conversorCaracter.conversorInvert(comissao);

        // valida os campos para que não ocorra erros
        validatorEmpregado.validatorEmpregados(nome, endereco, tipo, salario, comissao);

        if (tipo.equals("comissionado")) {
            // cria o empregado camossionado e salva ele no hashamp
            EmpregadoComissionado emprCom = new EmpregadoComissionado(nome, endereco, tipo, Float.parseFloat(salario),
                    Float.parseFloat(comissao));
            return EmpregadoController.AdicionarEmpregado(emprCom);
        }

        else {
            throw new ExceptionCriarEmpregado("Tipo invalido.");
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
            getDatabaseEmpregados.removeEmpregado(emp);
            Empregados.remove(emp);
        }

    }

}
