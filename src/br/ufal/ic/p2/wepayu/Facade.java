package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.DateInvalideException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.Exception.ExceptionRemoveEmpregado;
import br.ufal.ic.p2.wepayu.Exception.SaleAmountException;
import br.ufal.ic.p2.wepayu.controller.CardController;
import br.ufal.ic.p2.wepayu.controller.EmployeeController;
import br.ufal.ic.p2.wepayu.controller.SaleController;
import br.ufal.ic.p2.wepayu.controller.UnionServiceController;
import br.ufal.ic.p2.wepayu.middleware.UploadDatabase;
import br.ufal.ic.p2.wepayu.middleware.getDatabase;
import br.ufal.ic.p2.wepayu.utils.EnumType.getEnumDatabase;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.zip.DataFormatException;

public class Facade {

    UploadDatabase uplEmp;

    public Facade() throws FileNotFoundException {
        getDatabase.getDatabaseEntites(getEnumDatabase.Employee, EmployeeController.Empregados);
        getDatabase.getDatabaseEntites(getEnumDatabase.Unionized, UnionServiceController.employeesUnionzed);

    }

    public void zerarSistema() {
        EmployeeController.Empregados = new HashMap<>();
        UnionServiceController.employeesUnionzed = new HashMap<>();
        CardController.CartaoPontos = new HashMap<>();
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario)
            throws ExceptionCriarEmpregado {

        return EmployeeController.criarEmpregado(nome, endereco, tipo, salario);

    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao)
            throws ExceptionCriarEmpregado {

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

    public void encerrarSistema() throws ExceptionCriarEmpregado, EmpregadoNaoExisteException {
        // faz o upload dos empregados criados
        UploadDatabase.uploadData(getEnumDatabase.Employee, EmployeeController.Empregados);
        UploadDatabase.uploadData(getEnumDatabase.Unionized, UnionServiceController.employeesUnionzed);
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

    public void alteraEmpregado(String emp, String attribute, String value, String unionizedID, String unionFee)
            throws ExceptionGetEmpregado, EmpregadoNaoExisteException {

        if (emp.isEmpty()) {
            throw new ExceptionGetEmpregado("Identificacao do membro nao pode ser nula.");
        } else if (!EmployeeController.Empregados.containsKey(emp)) {
            throw new EmpregadoNaoExisteException();
        }

        EmployeeController.setEmployee(emp, attribute, value);

        UnionServiceController.createEmployeeUnionzed(emp, unionizedID, unionFee);

    }

    public void alteraEmpregado(String emp, String attribute, String value)
            throws ExceptionGetEmpregado, EmpregadoNaoExisteException {
        if (emp.isEmpty()) {
            throw new ExceptionGetEmpregado("Identificacao do membro nao pode ser nula.");
        } else if (!EmployeeController.Empregados.containsKey(emp)) {
            throw new EmpregadoNaoExisteException();
        }

        EmployeeController.setEmployee(emp, attribute, value);

    }

    public String getTaxasServico(String emp, String dateInitial, String deadline)
            throws ExceptionGetEmpregado, DateInvalideException, EmpregadoNaoExisteException {
        return UnionServiceController.getServiceFee(emp, dateInitial, deadline);
    }

    public void lancaTaxaServico(String unionizedID, String date, String value)
            throws DateInvalideException, ExceptionGetEmpregado {
        UnionServiceController.createServiceFee(unionizedID, date, value);
    }

}
