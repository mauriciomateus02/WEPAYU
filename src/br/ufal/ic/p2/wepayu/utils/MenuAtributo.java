package br.ufal.ic.p2.wepayu.utils;

import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.controller.humanResources.PaymentController;
import br.ufal.ic.p2.wepayu.controller.humanResources.ControllerFolhaPagamento;
import br.ufal.ic.p2.wepayu.controller.humanResources.UnionServiceController;
import br.ufal.ic.p2.wepayu.models.Employee.Employee;
import br.ufal.ic.p2.wepayu.models.Employee.Commissioned.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.Payment.PagamentoEmBanco;
import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;

public class MenuAtributo {
    public static String getValueatributo(Employee emp, String atributo) throws ExceptionGetEmpregado {

        String str;

        switch (atributo) {
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
                    PagamentoEmBanco payment = (PagamentoEmBanco) emp.getMethodPayment();
                    return payment.getbanco();
                } else {
                    throw new ExceptionGetEmpregado("Empregado nao recebe em banco.");
                }
            case "agencia":
                if (emp.getMethodPayment().getName().equals("banco")) {
                    PagamentoEmBanco payment = (PagamentoEmBanco) emp.getMethodPayment();
                    return payment.getagencia();
                } else {
                    throw new ExceptionGetEmpregado("Empregado nao recebe em banco.");
                }
            case "contaCorrente":
                if (emp.getMethodPayment().getName().equals("banco")) {
                    PagamentoEmBanco payment = (PagamentoEmBanco) emp.getMethodPayment();
                    return payment.getnumeroConta();
                } else {
                    throw new ExceptionGetEmpregado("Empregado nao recebe em banco.");
                }
            case "idSindicato":
                if (emp.getSindicalizado()) {
                    return emp.getUnionized().getIDsindicalizado();
                } else {
                    throw new ExceptionGetEmpregado("Empregado nao eh sindicalizado.");
                }
            case "taxaSindical":
                if (emp.getSindicalizado()) {
                    return Conversor.converterCharacter(emp.getUnionized().gettaxaSindicato());
                } else {
                    throw new ExceptionGetEmpregado("Empregado nao eh sindicalizado.");
                }
            case "agendaPagamento":
                return emp.getPaymentDay();
            default:
                throw new ExceptionGetEmpregado("Atributo nao existe.");
        }
    }

    public static void setvaloratributo(String emp, Employee employee, String atributo, String valoratributo,
            String unionID,
            String taxaSindicato) throws ExceptionGetEmpregado {

        switch (atributo) {
            case "sindicalizado":
                // verifica se o empregado já é sindicalizado, caso contrario altera
                if (employee.getSindicalizado()) {
                    UnionServiceController.setUnionized(emp, unionID, taxaSindicato);
                } else {
                    UnionServiceController.createEmployeeUnionzed(emp, unionID, taxaSindicato);
                    employee.setSindicalizado(Boolean.parseBoolean(valoratributo));
                }
                break;
            default:
                throw new ExceptionGetEmpregado("Atributo nao existe.");
        }

    }

    public static void setvaloratributo(String employeeID, Employee emp, String atributo, String valor)
            throws ExceptionGetEmpregado, NumberFormatException, ExceptionCriarEmpregado {
        // vwriavel para auxliar na conversão dos numeros
        float aux;
        switch (atributo) {

            case "sindicalizado":
                if (valor.equals("true") || valor.equals("false")) {

                    emp.setSindicalizado(Boolean.parseBoolean(valor));

                } else
                    throw new ExceptionGetEmpregado("Valor deve ser true ou false.");
                break;

            case "nome":
                if (valor.isEmpty())
                    throw new ExceptionGetEmpregado("Nome nao pode ser nulo.");
                else
                    emp.setNome(valor);
                break;

            case "endereco":
                if (valor.isEmpty())
                    throw new ExceptionGetEmpregado("Endereco nao pode ser nulo.");
                else
                    emp.setEndereco(valor);
                break;

            case "salario":
                if (valor.isEmpty()) {
                    throw new ExceptionGetEmpregado("Salario nao pode ser nulo.");
                } else {
                    valor = (valor.contains(",")) ?
                    // converte valores do tipo 20,00 em 20.00
                            Conversor.converterInvertedCharacter(valor) : valor;
                    // valida se o numero pode ser convertido
                    aux = Conversor.conversorNumeric(atributo, valor);
                    if (aux <= 0)
                        throw new ExceptionGetEmpregado("Salario deve ser nao-negativo.");
                    // atualiza o valor do salario
                    emp.setSalario(aux);
                }
                break;

            case "comissao":
                if (valor.isEmpty()) {
                    throw new ExceptionGetEmpregado("Comissao nao pode ser nula.");
                } else {
                    if (!emp.getTipo().equals("comissionado")) {
                        throw new ExceptionGetEmpregado("Empregado nao eh comissionado.");
                    } else {
                        // berifica a existencia de virgula para converter
                        valor = (valor.contains(",")) ? Conversor.converterInvertedCharacter(valor) : valor;
                        EmpregadoComissionado empr = (EmpregadoComissionado) emp;
                        // pega o retorno da função para ser atalizada no empregado
                        aux = Conversor.conversorNumeric(atributo, valor);
                        // verifica se o valor é menor que 0
                        if (aux <= 0)
                            throw new ExceptionGetEmpregado("Comissao deve ser nao-negativa.");
                        // atualiza o valor da comissao
                        empr.setComissao(aux);
                    }
                }
                break;
            case "tipo":
                if (valor.isEmpty()) {
                    throw new ExceptionGetEmpregado("tipo nao pode ser nulo.");
                } else {
                    Conversor.converterEmployee(employeeID, emp, valor);
                }
                break;

            case "metodoPagamento":
                PaymentController.MethodPayment(employeeID, valor);
                break;
            case "agendaPagamento":
                if (valor.isEmpty()) {
                    throw new ExceptionGetEmpregado("agenda de pagamnto nao pode ser nula");
                }
                if (ControllerFolhaPagamento.PaymentDays.contains(valor))
                    emp.setPaymentDay(valor);
                else {
                    throw new ExceptionCriarEmpregado("Agenda de pagamento nao esta disponivel");
                }
                break;
            default:
                throw new ExceptionGetEmpregado("Atributo nao existe.");
        }
    }

    public static void setvaloratributo(String employeeID, Employee emp, String atributo, String valor,
            String amount) throws ExceptionGetEmpregado, NumberFormatException, ExceptionCriarEmpregado {
        if (amount.isEmpty() && valor.equals("comissionado")) {
            throw new ExceptionGetEmpregado("comissao deve ser positiva.");
        } else if (amount.isEmpty() && valor.equals("horista")) {
            throw new ExceptionGetEmpregado("salario deve ser positiva.");
        }
        switch (atributo) {
            case "tipo":
                Conversor.converterEmployee(employeeID, emp, valor, amount);
                break;
            default:
                throw new ExceptionGetEmpregado("Atributo nao existe.");

        }
    }

}
