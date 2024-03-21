package br.ufal.ic.p2.wepayu.utils.Validator;

import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.controller.employee.ControllerEmpregado;
import br.ufal.ic.p2.wepayu.utils.EnumType.EnumContract;

public class ValidatorEmployee {

    public static void validatorEmpregados(String nome, String endereco, String tipo, String salario)
            throws ExceptionCriarEmpregado {

        validatorCampos(nome, endereco, tipo, salario);

        switch (tipo) {
            case "horista":
                validatorSalario(salario);
                break;

            case "comissionado":
                validatorSalario(salario);
                throw new ExceptionCriarEmpregado("Tipo nao aplicavel.");

            case "assalariado":
                validatorSalario(salario);
                break;

            default:
                throw new ExceptionCriarEmpregado("Tipo invalido.");
        }

    }

    public static void validatorEmpregados(String nome, String endereco, String tipo, String salario, String comissao)
            throws ExceptionCriarEmpregado {

        validatorCampos(nome, endereco, tipo, salario);

        switch (tipo) {
            case "horista":
                validatorSalario(salario);
                if (!comissao.isEmpty()) {
                    throw new ExceptionCriarEmpregado("Tipo nao aplicavel.");
                }
                break;

            case "comissionado":
                validatorSalario(salario);
                validatorComissao(comissao);
                break;

            case "assalariado":
                validatorSalario(salario);
                if (!comissao.isEmpty()) {
                    throw new ExceptionCriarEmpregado("Tipo nao aplicavel.");
                }
                break;

            default:
                throw new ExceptionCriarEmpregado("Tipo invalido.");
        }

    }

    private static void validatorCampos(String nome, String endereco, String tipo, String salario)
            throws ExceptionCriarEmpregado {
        if (nome.isEmpty()) {
            throw new ExceptionCriarEmpregado("Nome nao pode ser nulo.");
        }
        if (endereco.isEmpty()) {
            throw new ExceptionCriarEmpregado("Endereco nao pode ser nulo.");
        }
        if (tipo.isEmpty()) {
            throw new ExceptionCriarEmpregado("Tipo nao pode ser nulo.");
        }
        if (salario.isEmpty()) {
            throw new ExceptionCriarEmpregado("Salario nao pode ser nulo.");
        }

    }

    private static void validatorComissao(String comissao) throws ExceptionCriarEmpregado {
        if (comissao.isEmpty()) {
            throw new ExceptionCriarEmpregado("Comissao nao pode ser nula.");
        }
        try {
            float f = Float.parseFloat(comissao);
            if (f < 0) {
                throw new ExceptionCriarEmpregado("Comissao deve ser nao-negativa.");
            }
        } catch (NumberFormatException e) {
            throw new ExceptionCriarEmpregado("Comissao deve ser numerica.");
        }
    }

    private static void validatorSalario(String salario) throws ExceptionCriarEmpregado {
        try {
            float f = Float.parseFloat(salario);
            if (f < 0) {
                throw new ExceptionCriarEmpregado("Salario deve ser nao-negativo.");
            }
        } catch (NumberFormatException e) {
            throw new ExceptionCriarEmpregado("Salario deve ser numerico.");
        }
    }

    public static void EmployeeType(String emp, EnumContract type) throws ExceptionGetEmpregado {

        if (emp.isEmpty()) {
            throw new ExceptionGetEmpregado("Identificacao do empregado nao pode ser nula.");
        }

        if (ControllerEmpregado.Empregados.containsKey(emp)) {

            switch (type) {
                case COMMISSiONED:
                    if (!ControllerEmpregado.Empregados.get(emp).getTipo().equals("comissionado")) {
                        throw new ExceptionGetEmpregado("Empregado nao eh comissionado.");
                    }
                    break;

                case SALARIED:
                    if (!ControllerEmpregado.Empregados.get(emp).getTipo().equals("assalariado"))
                        throw new ExceptionGetEmpregado("Empregado nao eh assalariado.");
                    break;

                case HOURLY:
                    if (!ControllerEmpregado.Empregados.get(emp).getTipo().equals("horista"))
                        throw new ExceptionGetEmpregado("Empregado nao eh horista.");
                    break;

                default:
                    break;
            }

        } else {
            throw new ExceptionGetEmpregado("Empregado nao existe.");
        }
    }

}