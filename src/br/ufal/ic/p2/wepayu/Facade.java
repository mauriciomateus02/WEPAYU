package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.controller.employee.CardController;
import br.ufal.ic.p2.wepayu.controller.employee.EmployeeController;
import br.ufal.ic.p2.wepayu.controller.employee.SaleController;
import br.ufal.ic.p2.wepayu.controller.humanResources.PaymentController;
import br.ufal.ic.p2.wepayu.controller.humanResources.PayrollController;
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
        PayrollController.resetPaymentDay();
    }

    public void encerrarSistema() throws Exception {
        /* Faz upload dos empregados criados */
        DBHandler.uploadData(getEnumDatabase.Employee, EmployeeController.Empregados);
        DBHandler.uploadData(getEnumDatabase.Unionized, UnionServiceController.employeesUnionzed);
        DBHandler.uploadData(getEnumDatabase.Payment, PaymentController.methodsPayment);
        DBHandler.uploadData(getEnumDatabase.PaymentDay, PayrollController.PaymentDays);
    }

    public String criarEmpregado(String nome,
                                 String endereco,
                                 String tipo,
                                 String salario)
            throws Exception {
        return EmployeeController.criarEmpregado(nome, endereco, tipo, salario);
    }

    public String criarEmpregado(String nome,
                                 String endereco,
                                 String tipo,
                                 String salario,
                                 String comissao)
            throws Exception {
        return EmployeeController.criarEmpregado(nome, endereco, tipo, salario, comissao);
    }

    public String getAtributoEmpregado(String emp, String atributo)
            throws Exception {
        return EmployeeController.getAtributo(emp, atributo);
    }

    public String getEmpregadoPorNome(String nome, int indice)
            throws Exception {
        /* Para dada posição (n), temos um empregado de índice (n - 1) */
        return EmployeeController.getEmpregadoPorNome(nome, indice - 1);
    }

    public void removerEmpregado(String emp)
            throws Exception {
        EmployeeController.removerEmpregado(emp);
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

    public void lancaVenda(String emp, String date, String value)
            throws Exception {
        SaleController.registerSale(emp, date, value);
    }

    public String getVendasRealizadas(String emp, String dateInitial, String deadline)
            throws Exception {
        return SaleController.sumOfSalesAmount(emp, dateInitial, deadline);
    }

    public void alteraEmpregado(String emp,
                                String attribute,
                                String value,
                                String unionizedID,
                                String unionFee)
            throws Exception {
        EmployeeController.setEmployee(emp, attribute, value, unionizedID, unionFee);
    }

    public void alteraEmpregado(String emp, String attribute, String value)
            throws Exception {
        EmployeeController.setEmployee(emp, attribute, value);
    }

    public void alteraEmpregado(String emp,
                                String attribute,
                                String value,
                                String bank,
                                String agency,
                                String accountNumber)
            throws Exception {
        EmployeeController.setEmployee(emp, attribute, value, bank, agency, accountNumber);
    }

    public void alteraEmpregado(String emp,
                                String attribute,
                                String value,
                                String commission)
            throws Exception {

        EmployeeController.setEmployee(emp, attribute, value, commission);
    }

    public String getTaxasServico(String emp, String dateInitial, String deadline)
            throws Exception {
        return UnionServiceController.getServiceFee(emp, dateInitial, deadline);
    }

    public void lancaTaxaServico(String unionizedID, String date, String value)
            throws Exception {
        UnionServiceController.createServiceFee(unionizedID, date, value);
    }

    public String totalFolha(String date)
            throws Exception {
        return PayrollController.totalPayroll(date);
    }

    public void criarAgendaDePagamentos(String descricao) throws Exception {
        PayrollController.createPaymentDay(descricao);
    }

    public void rodaFolha(String data, String saida) throws Exception {
        PayrollController.generatePayroll(saida, data);
    }
}
