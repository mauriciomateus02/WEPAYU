package br.ufal.ic.p2.wepayu.controller.employee;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;
import br.ufal.ic.p2.wepayu.Exception.ExceptionGetEmpregado;
import br.ufal.ic.p2.wepayu.Exception.ExceptionRemoveEmpregado;
import br.ufal.ic.p2.wepayu.controller.humanResources.PaymentController;
import br.ufal.ic.p2.wepayu.middleware.DBHandler;
import br.ufal.ic.p2.wepayu.models.Employee.Employee;
import br.ufal.ic.p2.wepayu.models.Employee.Commissioned.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.Employee.Hourly.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.models.Employee.Salaried.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.utils.MenuAttribut;
import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;
import br.ufal.ic.p2.wepayu.utils.EnumType.getEnumDatabase;
import br.ufal.ic.p2.wepayu.utils.Validator.ValidatorEmployee;

public class EmployeeController {

    public static int index = 0;
    public static HashMap<String, Employee> Empregados;

    public static String criarEmpregado(String nome, String endereco, String tipo, String salario)
            throws ExceptionCriarEmpregado, ExceptionGetEmpregado {

        // transforma o valor do salario para poder ser convertido ex.: chega 20,00
        // transforma em 20.00
        salario = Conversor.converterInvertedCharacter(salario);

        // valida os campos para que não ocorra erros
        ValidatorEmployee.validatorEmpregados(nome, endereco, tipo, salario);

        if (tipo.equals("horista")) {
            EmpregadoHorista empregado = new EmpregadoHorista(nome, endereco, tipo, Float.parseFloat(salario));

            return EmployeeController.addEmployee(empregado);

        }

        else if (tipo.equals("assalariado")) {
            EmpregadoAssalariado empregadoAssalariado = new EmpregadoAssalariado(nome, endereco, tipo,
                    Float.parseFloat(salario));
            return EmployeeController.addEmployee(empregadoAssalariado);
        }

        else {
            throw new ExceptionCriarEmpregado("Tipo invalido.");
        }
    }

    public static String criarEmpregado(String nome, String endereco, String tipo, String salario, String comissao)
            throws ExceptionCriarEmpregado, ExceptionGetEmpregado {

        // transforma o valor do salario para poder ser convertido ex.: chega 20,00
        // transforma em 20.00
        salario = Conversor.converterInvertedCharacter(salario);

        // transforma a comissão em valor que pode ser convertido ex.: chega 20,00
        // transforma em 20.00
        comissao = Conversor.converterInvertedCharacter(comissao);

        // valida os campos para que não ocorra erros
        ValidatorEmployee.validatorEmpregados(nome, endereco, tipo, salario, comissao);

        if (tipo.equals("comissionado")) {
            // cria o empregado camossionado e salva ele no hashamp
            EmpregadoComissionado emprCom = new EmpregadoComissionado(nome, endereco, tipo, Float.parseFloat(salario),
                    Float.parseFloat(comissao));
            return EmployeeController.addEmployee(emprCom);
        }

        else {
            throw new ExceptionCriarEmpregado("Tipo invalido.");
        }

    }

    public static String addEmployee(Employee emp) throws ExceptionGetEmpregado {
        String id = Integer.toString(index);

        Empregados.put(id, emp);

        PaymentController.MethodPayment(id, "emMaos");

        index++;
        return id;

    }

    public static String getEmpregadoPorNome(String nome, int id) throws ExceptionGetEmpregado {

        for (Map.Entry<String, Employee> emp : EmployeeController.Empregados.entrySet()) {

            if (emp.getValue().getNome().equals(nome) && id == 0) {
                return emp.getKey();
            } else if (emp.getValue().getNome().equals(nome) && id > 0) {
                id--;
            }
        }
        throw new ExceptionGetEmpregado("Nao ha empregado com esse nome.");
    }

    public static String getAtributo(String emp, String attribut)

            throws EmpregadoNaoExisteException, ExceptionGetEmpregado {

        if (emp.isEmpty()) {
            throw new ExceptionGetEmpregado("Identificacao do empregado nao pode ser nula.");
        } else if (!EmployeeController.Empregados.containsKey(emp)) {
            throw new EmpregadoNaoExisteException();
        }

        Employee empr;
        // empr receberá empregado do hashmap
        empr = EmployeeController.Empregados.get(emp);

        // exibe o atributo buscado pelo usuário do empregado desejado
        return MenuAttribut.getValueAttribut(empr, attribut);
    }

    public static void removerEmpregado(String emp)
            throws EmpregadoNaoExisteException, ExceptionRemoveEmpregado, FileNotFoundException {
        // verifica se o id é nulo
        if (emp.isEmpty()) {
            throw new ExceptionRemoveEmpregado();
        } else if (Empregados.get(emp) == null) {
            throw new EmpregadoNaoExisteException();
        } else {
            DBHandler.removeData(getEnumDatabase.Employee, emp);
            Empregados.remove(emp);
        }

    }

    public static void setEmployee(String emp, String attribut, String value)
            throws ExceptionGetEmpregado, EmpregadoNaoExisteException, NumberFormatException, ExceptionCriarEmpregado {
        if (emp.isEmpty()) {
            throw new ExceptionGetEmpregado("Identificacao do empregado nao pode ser nula.");
        } else if (!EmployeeController.Empregados.containsKey(emp)) {
            throw new ExceptionGetEmpregado("Empregado nao existe");
        }
        Employee employee = EmployeeController.Empregados.get(emp);

        MenuAttribut.setValueAttribut(emp, employee, attribut, value);

    }

    // alteracao do empregadp para receber no banco
    public static void setEmployee(String emp, String attribut, String value, String bank, String agency,
            String accountNumber) throws ExceptionGetEmpregado {
        if (emp.isEmpty()) {
            throw new ExceptionGetEmpregado("Identificacao do empregado nao pode ser nula.");
        } else if (!EmployeeController.Empregados.containsKey(emp)) {
            throw new ExceptionGetEmpregado("Empregado nao existe.");
        }

        PaymentController.MethodPayment(emp, value, bank, agency, accountNumber);

    }

    public static void setEmployee(String emp, String attribut, String value, String commission)
            throws ExceptionGetEmpregado, NumberFormatException, ExceptionCriarEmpregado, EmpregadoNaoExisteException {
        if (emp.isEmpty())
            throw new ExceptionGetEmpregado("Identificacao do empregado nao pode ser nula.");
        if (attribut.isEmpty())
            throw new ExceptionGetEmpregado("Atributo nao pode ser nulo.");
        if (value.isEmpty())
            throw new ExceptionGetEmpregado("Tipo nao pode ser nulo.");
        if (!Empregados.containsKey(emp))
            throw new EmpregadoNaoExisteException();

        Employee employee = Empregados.get(emp);

        MenuAttribut.setValueAttribut(emp, employee, attribut, value, commission);

    }

    // alteração do empregado sindicalizado
    public static void setEmployee(String emp, String attribut, String value, String unionID, String unionFee)
            throws ExceptionGetEmpregado, EmpregadoNaoExisteException, NumberFormatException, ExceptionCriarEmpregado {
        if (emp.isEmpty())
            throw new ExceptionGetEmpregado("Identificacao do empregado nao pode ser nula.");
        if (attribut.isEmpty())
            throw new ExceptionGetEmpregado("Atributo nao pode ser nulo.");
        if (value.isEmpty())
            throw new ExceptionGetEmpregado("Tipo nao pode ser nulo.");
        if (!Empregados.containsKey(emp))
            throw new EmpregadoNaoExisteException();

        Employee employee = Empregados.get(emp);
        // chama a função que vai verificar e alterar o empregado sindicalizado.
        MenuAttribut.setValueAttribut(emp, employee, attribut, value, unionID, unionFee);

    }
}
