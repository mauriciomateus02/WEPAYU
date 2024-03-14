package br.ufal.ic.p2.wepayu.utils;

import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.controller.humanResources.PaymentController;
import br.ufal.ic.p2.wepayu.controller.humanResources.UnionServiceController;
import br.ufal.ic.p2.wepayu.models.Employee.Employee;
import br.ufal.ic.p2.wepayu.models.Employee.Commissioned.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.Payment.PaymentInBank;
import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;

public class MenuAttribut {
    public static String getValueAttribut(Employee emp, String attribut) throws ExceptionGetEmpregado {

        String str;

        switch (attribut) {
            case "nome":
                return emp.getNome();

            case "endereco":
                return emp.getEndereco();

            case "tipo":
                return emp.getTipo();

            case "sindicalizado":
                return emp.getSindicalizado().toString();

            case "salario":
                str = emp.getSalario();
                return Conversor.converterCharacter(str);

            case "comissao":
                if (emp.getTipo().equals("comissionado")) {
                    EmpregadoComissionado empre = (EmpregadoComissionado) emp;
                    str = empre.getComissao();
                    return Conversor.converterCharacter(str);
                } else {
                    throw new ExceptionGetEmpregado("Empregado nao eh comissionado.");
                }
            case "metodoPagamento":
                return emp.getMethodPayment().getName();
            case "banco":
                if (emp.getMethodPayment().getName().equals("banco")) {
                    PaymentInBank payment = (PaymentInBank) emp.getMethodPayment();
                    return payment.getBank();
                } else {
                    throw new ExceptionGetEmpregado("Empregado nao recebe em banco.");
                }
            case "agencia":
                if (emp.getMethodPayment().getName().equals("banco")) {
                    PaymentInBank payment = (PaymentInBank) emp.getMethodPayment();
                    return payment.getAgency();
                } else {
                    throw new ExceptionGetEmpregado("Empregado nao recebe em banco.");
                }
            case "contaCorrente":
                if (emp.getMethodPayment().getName().equals("banco")) {
                    PaymentInBank payment = (PaymentInBank) emp.getMethodPayment();
                    return payment.getAccountNumber();
                } else {
                    throw new ExceptionGetEmpregado("Empregado nao recebe em banco.");
                }
            case "idSindicato":
                if (emp.getSindicalizado()) {
                    return emp.getUnionized().getUnionizedID();
                } else {
                    throw new ExceptionGetEmpregado("Empregado nao eh sindicalizado.");
                }
            case "taxaSindical":
                if (emp.getSindicalizado()) {
                    return Conversor.converterCharacter(emp.getUnionized().getUnionFee());
                } else {
                    throw new ExceptionGetEmpregado("Empregado nao eh sindicalizado.");
                }
            default:
                throw new ExceptionGetEmpregado("Atributo nao existe.");
        }
    }

    public static void setValueAttribut(String emp, Employee employee, String attribut, String valueAttribut,
            String unionID,
            String unionfee) throws ExceptionGetEmpregado {

        switch (attribut) {
            case "sindicalizado":
                // verifica se o empregado já é sindicalizado, caso contrario altera
                if (employee.getSindicalizado()) {
                    UnionServiceController.setUnionized(emp, unionID, unionfee);
                } else {
                    UnionServiceController.createEmployeeUnionzed(emp, unionID, unionfee);
                    employee.setSindicalizado(Boolean.parseBoolean(valueAttribut));
                }
                break;
            default:
                throw new ExceptionGetEmpregado("Atributo nao existe.");
        }

    }

    public static void setValueAttribut(String employeeID, Employee emp, String attribut, String value)
            throws ExceptionGetEmpregado, NumberFormatException, ExceptionCriarEmpregado {
        // vwriavel para auxliar na conversão dos numeros
        float aux;
        switch (attribut) {

            case "sindicalizado":
                if (value.equals("true") || value.equals("false")) {

                    emp.setSindicalizado(Boolean.parseBoolean(value));

                } else
                    throw new ExceptionGetEmpregado("Valor deve ser true ou false.");
                break;

            case "nome":
                if (value.isEmpty())
                    throw new ExceptionGetEmpregado("Nome nao pode ser nulo.");
                else
                    emp.setNome(value);
                break;

            case "endereco":
                if (value.isEmpty())
                    throw new ExceptionGetEmpregado("Endereco nao pode ser nulo.");
                else
                    emp.setEndereco(value);
                break;

            case "salario":
                if (value.isEmpty()) {
                    throw new ExceptionGetEmpregado("Salario nao pode ser nulo.");
                } else {
                    value = (value.contains(",")) ?
                    // converte valores do tipo 20,00 em 20.00
                            Conversor.converterInvertedCharacter(value) : value;
                    // valida se o numero pode ser convertido
                    aux = Conversor.conversorNumeric(attribut, value);
                    if (aux <= 0)
                        throw new ExceptionGetEmpregado("Salario deve ser nao-negativo.");
                    // atualiza o valor do salario
                    emp.setSalario(aux);
                }
                break;

            case "comissao":
                if (value.isEmpty()) {
                    throw new ExceptionGetEmpregado("Comissao nao pode ser nula.");
                } else {
                    if (!emp.getTipo().equals("comissionado")) {
                        throw new ExceptionGetEmpregado("Empregado nao eh comissionado.");
                    } else {
                        // berifica a existencia de virgula para converter
                        value = (value.contains(",")) ? Conversor.converterInvertedCharacter(value) : value;
                        EmpregadoComissionado empr = (EmpregadoComissionado) emp;
                        // pega o retorno da função para ser atalizada no empregado
                        aux = Conversor.conversorNumeric(attribut, value);
                        // verifica se o valor é menor que 0
                        if (aux <= 0)
                            throw new ExceptionGetEmpregado("Comissao deve ser nao-negativa.");
                        // atualiza o valor da comissao
                        empr.setComissao(aux);
                    }
                }
                break;
            case "tipo":
                if (value.isEmpty()) {
                    throw new ExceptionGetEmpregado("tipo nao pode ser nulo.");
                } else {
                    Conversor.converterEmployee(employeeID, emp, value);
                }
                break;

            case "metodoPagamento":
                PaymentController.MethodPayment(employeeID, value);
                break;
            default:
                throw new ExceptionGetEmpregado("Atributo nao existe.");
        }
    }

    public static void setValueAttribut(String employeeID, Employee emp, String attribut, String value,
            String amount) throws ExceptionGetEmpregado, NumberFormatException, ExceptionCriarEmpregado {
        if (amount.isEmpty() && value.equals("comissionado")) {
            throw new ExceptionGetEmpregado("comissao deve ser positiva.");
        } else if (amount.isEmpty() && value.equals("horista")) {
            throw new ExceptionGetEmpregado("salario deve ser positiva.");
        }
        switch (attribut) {
            case "tipo":
                Conversor.converterEmployee(employeeID, emp, value, amount);
                break;
            default:
                throw new ExceptionGetEmpregado("Atributo nao existe.");

        }
    }

}
