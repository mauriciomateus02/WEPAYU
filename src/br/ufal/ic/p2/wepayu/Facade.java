package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.controller.employee.CardController;
import br.ufal.ic.p2.wepayu.controller.employee.ControllerEmpregado;
import br.ufal.ic.p2.wepayu.controller.employee.ControllerVenda;
import br.ufal.ic.p2.wepayu.controller.humanResources.PaymentController;
import br.ufal.ic.p2.wepayu.controller.humanResources.ControllerFolhaPagamento;
import br.ufal.ic.p2.wepayu.controller.humanResources.UnionServiceController;
import br.ufal.ic.p2.wepayu.middleware.DBHandler;
import br.ufal.ic.p2.wepayu.utils.EnumType.getEnumDatabase;

import java.io.FileNotFoundException;

public class Facade {

    public Facade() throws FileNotFoundException {
        DBHandler.getData(getEnumDatabase.Employee);
        DBHandler.getData(getEnumDatabase.Unionized);
        DBHandler.getData(getEnumDatabase.Payment);
        DBHandler.getData(getEnumDatabase.PaymentDay);
    }

    public void zerarSistema() throws FileNotFoundException {
        DBHandler.resetData(getEnumDatabase.Employee);
        DBHandler.resetData(getEnumDatabase.Unionized);
        DBHandler.resetData(getEnumDatabase.Payment);
        DBHandler.resetData(getEnumDatabase.PaymentDay);
        ControllerFolhaPagamento.resetPaymentDay();
    }

    public void encerrarSistema() throws Exception {
        /* Faz upload dos empregados criados */
        DBHandler.uploadData(getEnumDatabase.Employee, ControllerEmpregado.Empregados);
        DBHandler.uploadData(getEnumDatabase.Unionized, UnionServiceController.employeesUnionzed);
        DBHandler.uploadData(getEnumDatabase.Payment, PaymentController.methodsPayment);
        DBHandler.uploadData(getEnumDatabase.PaymentDay, ControllerFolhaPagamento.PaymentDays);
    }

    public String criarEmpregado(String nome,
                                 String endereco,
                                 String tipo,
                                 String salario)
            throws Exception {
        /* Cria empregado padrão */
        return ControllerEmpregado.criarEmpregado(nome, endereco, tipo, salario);
    }

    public String criarEmpregado(String nome,
                                 String endereco,
                                 String tipo,
                                 String salario,
                                 String comissao)
            throws Exception {
        /* Cria empregado comissionado */
        return ControllerEmpregado.criarEmpregado(nome, endereco, tipo, salario, comissao);
    }

    public String getAtributoEmpregado(String emp, String atributo)
            throws Exception {
        return ControllerEmpregado.getAtributo(emp, atributo);
    }

    public String getEmpregadoPorNome(String nome, int indice)
            throws Exception {
        /* Para dado índice (n), temos empregado (n - 1) */
        return ControllerEmpregado.getEmpregadoPorNome(nome, indice - 1);
    }

    public void removerEmpregado(String emp)
            throws Exception {
        ControllerEmpregado.removerEmpregado(emp);
    }

    public void lancaCartao(String emp, String data, String hora)
            throws Exception {
        CardController.lancaCartao(emp, data, hora);
    }

    public String getHorasNormaisTrabalhadas(String emp, String dataInicio, String dataFinal)
            throws Exception {
        return CardController.getHoras(emp, "normal", dataInicio, dataFinal);
    }

    public String getHorasExtrasTrabalhadas(String emp, String dataInicio, String dataFinal)
            throws Exception {
        return CardController.getHoras(emp, "extra", dataInicio, dataFinal);
    }

    public void lancaVenda(String emp, String date, String valor)
            throws Exception {
        ControllerVenda.registerSale(emp, date, valor);
    }

    public String getVendasRealizadas(String emp, String dataInicial, String prazo)
            throws Exception {
        return ControllerVenda.sumOfSalesAmount(emp, dataInicial, prazo);
    }

    public void alteraEmpregado(String emp,
                                String atributo,
                                String valor,
                                String IDsindicalizado,
                                String taxaSindicato)
            throws Exception {
        /* Altera empregado sindicalizado */
        if (emp.isEmpty()) {
            throw new ExceptionGetEmpregado("Identificacao do empregado nao pode ser nula.");
        } else if (!ControllerEmpregado.Empregados.containsKey(emp)) {
            throw new EmpregadoNaoExisteException();
        }
        ControllerEmpregado.setEmployee(emp, atributo, valor, IDsindicalizado, taxaSindicato);
    }

    public void alteraEmpregado(String emp, String atributo, String valor)
            throws Exception {
        /* Altera empregado padrão */
        ControllerEmpregado.setEmployee(emp, atributo, valor);
    }

    public void alteraEmpregado(String emp,
                                String atributo,
                                String valor,
                                String banco,
                                String agencia,
                                String numeroConta) throws Exception {
        /* Altera empregado com conta bancária */
        ControllerEmpregado.setEmployee(emp, atributo, valor, banco, agencia, numeroConta);
    }

    public void alteraEmpregado(String emp,
                                String atributo,
                                String valor,
                                String commission)
            throws Exception {
        /* Altera empregado comissionado */
        ControllerEmpregado.setEmployee(emp, atributo, valor, commission);
    }

    public String getTaxasServico(String emp, String dataInicial, String prazo)
            throws Exception {
        return UnionServiceController.getServiceFee(emp, dataInicial, prazo);
    }

    public void lancaTaxaServico(String IDsindicalizado, String date, String valor)
            throws Exception {
        UnionServiceController.createServiceFee(IDsindicalizado, date, valor);
    }

    public String totalFolha(String date)
            throws Exception {
        return ControllerFolhaPagamento.totalPayroll(date);
    }

    public void criarAgendaDePagamentos(String descricao) throws Exception {
        ControllerFolhaPagamento.createPaymentDay(descricao);
    }
}
