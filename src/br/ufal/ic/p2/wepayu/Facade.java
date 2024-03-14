package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.DateInvalideException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.Exception.ExceptionRemoveEmpregado;
import br.ufal.ic.p2.wepayu.Exception.SaleAmountException;
import br.ufal.ic.p2.wepayu.controller.employee.CardController;
import br.ufal.ic.p2.wepayu.controller.employee.EmployeeController;
import br.ufal.ic.p2.wepayu.controller.employee.SaleController;
import br.ufal.ic.p2.wepayu.controller.humanResources.PaymentController;
import br.ufal.ic.p2.wepayu.controller.humanResources.PayrollController;
import br.ufal.ic.p2.wepayu.controller.humanResources.UnionServiceController;
import br.ufal.ic.p2.wepayu.middleware.DBHandler;
import br.ufal.ic.p2.wepayu.utils.EnumType.getEnumDatabase;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

public class Facade {

    public Facade() throws FileNotFoundException {
        DBHandler.getData(getEnumDatabase.Employee);
        DBHandler.getData(getEnumDatabase.Unionized);
        DBHandler.getData(getEnumDatabase.Payment);

    }

    public void zerarSistema() throws FileNotFoundException {

        DBHandler.resetData(getEnumDatabase.Employee);
        DBHandler.resetData(getEnumDatabase.Unionized);
        DBHandler.resetData(getEnumDatabase.Payment);
    }

    public void encerrarSistema() throws ExceptionCriarEmpregado, EmpregadoNaoExisteException {
        // faz o upload dos empregados criados

        DBHandler.uploadData(getEnumDatabase.Employee, EmployeeController.Empregados);
        DBHandler.uploadData(getEnumDatabase.Unionized, UnionServiceController.employeesUnionzed);
        DBHandler.uploadData(getEnumDatabase.Payment, PaymentController.methodsPayment);
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario)
            throws ExceptionCriarEmpregado, ExceptionGetEmpregado {

        return EmployeeController.criarEmpregado(nome, endereco, tipo, salario);

    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao)
            throws ExceptionCriarEmpregado, ExceptionGetEmpregado {

        return EmployeeController.criarEmpregado(nome, endereco, tipo, salario, comissao);

    }

    public String getAtributoEmpregado(String emp, String atributo)
            throws EmpregadoNaoExisteException, ExceptionGetEmpregado {

        return EmployeeController.getAtributo(emp, atributo);

    }

    public String getEmpregadoPorNome(String nome, int indice) throws ExceptionGetEmpregado {
        // como o usuário está buscando os empregados pelo nome o indice significa a
        // posição dele
        // está e n-1 sendo n o indice buscado
        return EmployeeController.getEmpregadoPorNome(nome, indice - 1);
    }

    public void removerEmpregado(String emp)
            throws EmpregadoNaoExisteException, ExceptionRemoveEmpregado, FileNotFoundException {
        EmployeeController.removerEmpregado(emp);
    }

    public void lancaCartao(String emp, String data, String hora)
            throws EmpregadoNaoExisteException, DataFormatException, DateInvalideException, ExceptionGetEmpregado {

        CardController.lancaCartao(emp, data, hora);
    }

    public String getHorasNormaisTrabalhadas(String emp, String dataInicio, String dataFinal)
            throws ExceptionGetEmpregado, DataFormatException, DateInvalideException, EmpregadoNaoExisteException {

        return CardController.getHoras(emp, "normal", dataInicio, dataFinal);
    }

    public String getHorasExtrasTrabalhadas(String emp, String dataInicio, String dataFinal)
            throws ExceptionGetEmpregado, DataFormatException, DateInvalideException, EmpregadoNaoExisteException {
        return CardController.getHoras(emp, "extra", dataInicio, dataFinal);
    }

    public void lancaVenda(String emp, String date, String value)
            throws DateInvalideException, SaleAmountException, ExceptionGetEmpregado {

        SaleController.registerSale(emp, date, value);
    }

    public String getVendasRealizadas(String emp, String dateInitial, String deadline)
            throws DateInvalideException, ExceptionGetEmpregado {
        return SaleController.sumOfSalesAmount(emp, dateInitial, deadline);
    }

    public void alteraEmpregado(String emp, String attribut, String value, String unionizedID, String unionFee)
            throws ExceptionGetEmpregado, EmpregadoNaoExisteException, NumberFormatException, ExceptionCriarEmpregado {

        if (emp.isEmpty()) {
            throw new ExceptionGetEmpregado("Identificacao do empregado nao pode ser nula.");
        } else if (!EmployeeController.Empregados.containsKey(emp)) {
            throw new EmpregadoNaoExisteException();
        }

        EmployeeController.setEmployee(emp, attribut, value, unionizedID, unionFee);

    }

    public void alteraEmpregado(String emp, String attribut, String value)
            throws ExceptionGetEmpregado, EmpregadoNaoExisteException, NumberFormatException, ExceptionCriarEmpregado {
        if (emp.isEmpty()) {
            throw new ExceptionGetEmpregado("Identificacao do empregado nao pode ser nula.");
        } else if (!EmployeeController.Empregados.containsKey(emp)) {
            throw new EmpregadoNaoExisteException();
        }

        EmployeeController.setEmployee(emp, attribut, value);

    }

    public void alteraEmpregado(String emp, String attribut, String value, String bank, String agency,
            String accountNumber) throws ExceptionGetEmpregado {

        EmployeeController.setEmployee(emp, attribut, value, bank, agency, accountNumber);

    }

    public void alteraEmpregado(String emp, String attribut, String value, String commission)
            throws NumberFormatException, ExceptionGetEmpregado, ExceptionCriarEmpregado, EmpregadoNaoExisteException {
        EmployeeController.setEmployee(emp, attribut, value, commission);
    }

    public String getTaxasServico(String emp, String dateInitial, String deadline)
            throws ExceptionGetEmpregado, DateInvalideException, EmpregadoNaoExisteException {
        return UnionServiceController.getServiceFee(emp, dateInitial, deadline);
    }

    public void lancaTaxaServico(String unionizedID, String date, String value)
            throws DateInvalideException, ExceptionGetEmpregado {
        UnionServiceController.createServiceFee(unionizedID, date, value);
    }

    public String totalFolha(String date)
            throws ExceptionGetEmpregado, DateInvalideException, DataFormatException, EmpregadoNaoExisteException {
        return PayrollController.totalPayroll(date);
    }
}
