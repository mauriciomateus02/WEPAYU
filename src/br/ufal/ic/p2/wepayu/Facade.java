package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.controller.employee.CardController;
import br.ufal.ic.p2.wepayu.controller.employee.EmployeeController;
import br.ufal.ic.p2.wepayu.controller.employee.SaleController;
import br.ufal.ic.p2.wepayu.controller.humanResources.PaymentController;
import br.ufal.ic.p2.wepayu.controller.humanResources.PayrollController;
import br.ufal.ic.p2.wepayu.controller.humanResources.UnionServiceController;
import br.ufal.ic.p2.wepayu.middleware.DBHandler;
import br.ufal.ic.p2.wepayu.middleware.serviceDatabase.GetFiles;
import br.ufal.ic.p2.wepayu.middleware.serviceDatabase.PushFiles;
import br.ufal.ic.p2.wepayu.middleware.serviceDatabase.resetFiles;
import br.ufal.ic.p2.wepayu.models.StrategyDB.CreateEntityDB;
import br.ufal.ic.p2.wepayu.models.StrategyDB.DBConnection;
import br.ufal.ic.p2.wepayu.utils.EnumType.getEnumDatabase;

public class Facade {

    public Facade() throws Exception {
        DBConnection db = new GetFiles();
        DBHandler dbHandler = new DBHandler();
        dbHandler.getData(getEnumDatabase.Employee, db);
        dbHandler.getData(getEnumDatabase.Unionized, db);
        dbHandler.getData(getEnumDatabase.Payment, db);
        dbHandler.getData(getEnumDatabase.PaymentDay, db);
    }

    public void zerarSistema() throws Exception {

        DBConnection db = new resetFiles();
        DBHandler dbHandler = new DBHandler();

        dbHandler.resetData(getEnumDatabase.Employee, db);
        dbHandler.resetData(getEnumDatabase.Unionized, db);
        dbHandler.resetData(getEnumDatabase.Payment, db);
        dbHandler.resetData(getEnumDatabase.PaymentDay, db);
        PayrollController.resetPaymentDay();
    }

    public void encerrarSistema() throws Exception {
        /* Faz upload dos empregados criados */
        CreateEntityDB db = new PushFiles();
        DBHandler dbHandler = new DBHandler();

        dbHandler.uploadData(getEnumDatabase.Employee, db, EmployeeController.Empregados);
        dbHandler.uploadData(getEnumDatabase.Unionized, db, UnionServiceController.employeesUnionzed);
        dbHandler.uploadData(getEnumDatabase.Payment, db, PaymentController.methodsPayment);
        dbHandler.uploadData(getEnumDatabase.PaymentDay, db, PayrollController.PaymentDays);
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario)
            throws Exception {

        return EmployeeController.criarEmpregado(nome, endereco, tipo, salario);
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao)
            throws Exception {

        return EmployeeController.criarEmpregado(nome, endereco, tipo, salario, comissao);
    }

    public String getAtributoEmpregado(String emp, String atributo)
            throws Exception {

        return EmployeeController.getAtributo(emp, atributo);
    }

    public String getEmpregadoPorNome(String nome, int indice) throws Exception {
        // como o usuário está buscando os empregados pelo nome o indice significa a
        // posição dele
        // está e n-1 sendo n o indice buscado

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

    public void alteraEmpregado(String emp, String attribut, String value, String unionizedID, String unionFee)
            throws Exception {

        EmployeeController.setEmployee(emp, attribut, value, unionizedID, unionFee);
    }

    public void alteraEmpregado(String emp, String attribut, String value)
            throws Exception {

        EmployeeController.setEmployee(emp, attribut, value);
    }

    public void alteraEmpregado(String emp, String attribut, String value, String bank, String agency,
            String accountNumber) throws Exception {

        EmployeeController.setEmployee(emp, attribut, value, bank, agency, accountNumber);
    }

    public void alteraEmpregado(String emp, String attribut, String value, String commission)
            throws Exception {

        EmployeeController.setEmployee(emp, attribut, value, commission);
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
