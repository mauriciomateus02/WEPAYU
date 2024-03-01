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
import br.ufal.ic.p2.wepayu.middleware.uploadEmpregados;
import br.ufal.ic.p2.wepayu.middleware.getDatabaseCartoesPonto;
import br.ufal.ic.p2.wepayu.middleware.getDatabaseEmployee;
import br.ufal.ic.p2.wepayu.middleware.uploadCartoes;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.zip.DataFormatException;

public class Facade {

    uploadEmpregados uplEmp;
    uploadCartoes uploadCartoes;

    public Facade() throws FileNotFoundException {
        getDatabaseEmployee.getDatabaseEmpregado();
        getDatabaseCartoesPonto.getDatabaseCartoes();
    }

    public void zerarSistema() {
        EmployeeController.Empregados = new HashMap<>();
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
        uplEmp = new uploadEmpregados();
        uploadCartoes = new uploadCartoes();
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
}
