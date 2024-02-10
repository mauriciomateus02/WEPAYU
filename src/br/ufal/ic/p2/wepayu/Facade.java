package br.ufal.ic.p2.wepayu;

import br.ufal.ic.p2.wepayu.Exception.DataInvalidaException;
import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.Exception.ExceptionRemoveEmpregado;
import br.ufal.ic.p2.wepayu.controller.CartaoController;
import br.ufal.ic.p2.wepayu.controller.EmpregadoController;
import br.ufal.ic.p2.wepayu.controller.uploadCartoes;
import br.ufal.ic.p2.wepayu.controller.uploadEmpregados;
import br.ufal.ic.p2.wepayu.utils.getDatabaseCartoesPonto;
import br.ufal.ic.p2.wepayu.utils.getDatabaseEmpregados;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.zip.DataFormatException;

public class Facade {

    uploadEmpregados uplEmp;
    uploadCartoes uploadCartoes;

    public Facade() throws FileNotFoundException {
        getDatabaseEmpregados.getDatabaseEmpregado();
        getDatabaseCartoesPonto.getDatabaseCartoes();
    }

    public void zerarSistema() {
        EmpregadoController.Empregados = new HashMap<>();
        CartaoController.CartaoPontos = new HashMap<>();
    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario)
            throws ExceptionCriarEmpregado {

        return EmpregadoController.criarEmpregado(nome, endereco, tipo, salario);

    }

    public String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao)
            throws ExceptionCriarEmpregado {

        return EmpregadoController.criarEmpregado(nome, endereco, tipo, salario, comissao);

    }

    public String getAtributoEmpregado(String emp, String atributo)
            throws EmpregadoNaoExisteException, ExceptionGetEmpregado {

        return EmpregadoController.getAtributo(emp, atributo);

    }

    public String getEmpregadoPorNome(String nome, int indice) throws ExceptionGetEmpregado {
        // como o usuário está buscando os empregados pelo nome o indice significa a
        // posição dele
        // está e n-1 sendo n o indice buscado
        return EmpregadoController.getEmpregadoPorNome(nome, indice - 1);
    }

    public void encerrarSistema() throws ExceptionCriarEmpregado, EmpregadoNaoExisteException {
        uplEmp = new uploadEmpregados();
        uploadCartoes = new uploadCartoes();
    }

    public void removerEmpregado(String emp)
            throws EmpregadoNaoExisteException, ExceptionRemoveEmpregado, FileNotFoundException {
        EmpregadoController.removerEmpregado(emp);
    }

    public void lancaCartao(String emp, String data, String hora)
            throws EmpregadoNaoExisteException, DataFormatException, DataInvalidaException, ExceptionGetEmpregado {

        CartaoController.lancaCartao(emp, data, hora);
    }

    public String getHorasNormaisTrabalhadas(String emp, String dataInicio, String dataFinal)
            throws ExceptionGetEmpregado, DataFormatException, DataInvalidaException, EmpregadoNaoExisteException {

        return CartaoController.getHoras(emp, "normal", dataInicio, dataFinal);
    }

    public String getHorasExtrasTrabalhadas(String emp, String dataInicio, String dataFinal)
            throws ExceptionGetEmpregado, DataFormatException, DataInvalidaException, EmpregadoNaoExisteException {
        return CartaoController.getHoras(emp, "extra", dataInicio, dataFinal);
    }

}
