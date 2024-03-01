package br.ufal.ic.p2.wepayu.middleware;

import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;
import br.ufal.ic.p2.wepayu.controller.EmployeeController;
import br.ufal.ic.p2.wepayu.models.CartaoPontos;
import br.ufal.ic.p2.wepayu.models.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.models.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.models.Sale;

public class setDataEmployee {

    public static void setEmployee(String index, String name, String address, String type, String unionized,
            float salary, String[] list) throws ExceptionCriarEmpregado {

        if (type.equals("horista")) {
            // recria o funcionario horista
            EmpregadoHorista emp = new EmpregadoHorista(name, address, type, salary);
            // informa se o empregado é sindicalizado ou não.
            emp.setSindicalizado(Boolean.parseBoolean(unionized));

            for (String cardPoint : list) {

                String[] date = cardPoint.split(";");

                CartaoPontos card = new CartaoPontos(date[0], Float.parseFloat(date[1]));

                emp.addCartaoPontos(card);
            }

            EmployeeController.Empregados.put(index, emp);

        }

        EmployeeController.index = Integer.parseInt(index);
    }

    public static void setEmployee(String index, String name, String address, String type, String unionized,
            float salary) throws ExceptionCriarEmpregado {

        if (type.equals("horista")) {
            // recria o funcionario horista
            EmpregadoHorista emp = new EmpregadoHorista(name, address, type, salary);
            // informa se o empregado é sindicalizado ou não.
            emp.setSindicalizado(Boolean.parseBoolean(unionized));

            EmployeeController.Empregados.put(index, emp);
        } else if (type.equals("assalariado")) {
            EmpregadoAssalariado emp = new EmpregadoAssalariado(name, address, type, salary);

            emp.setSindicalizado(Boolean.parseBoolean(unionized));

            EmployeeController.Empregados.put(index, emp);
        }

        EmployeeController.index = Integer.parseInt(index);
    }

    public static void setEmployee(String index, String name, String address, String type, String unionized,
            float salary, float commission, String[] list) throws ExceptionCriarEmpregado {

        if (type.equals("comissionado")) {
            // recria o funcionario horista
            EmpregadoComissionado emp = new EmpregadoComissionado(name, address, type, salary, commission);

            // informa se o empregado é sindicalizado ou não.
            emp.setSindicalizado(Boolean.parseBoolean(unionized));

            for (String cardPoint : list) {

                String[] date = cardPoint.split(";");

                Sale sale = new Sale(date[0], Float.parseFloat(date[1]));

                emp.addSale(sale);
            }

            EmployeeController.Empregados.put(index, emp);

        }

        EmployeeController.index = Integer.parseInt(index);
    }

    public static void setEmployee(String index, String name, String address, String type, String unionized,
            float salary, float commission) throws ExceptionCriarEmpregado {

        if (type.equals("comissionado")) {
            // recria o funcionario horista
            EmpregadoComissionado emp = new EmpregadoComissionado(name, address, type, salary, commission);
            // informa se o empregado é sindicalizado ou não.
            emp.setSindicalizado(Boolean.parseBoolean(unionized));

            EmployeeController.Empregados.put(index, emp);
        }

        EmployeeController.index = Integer.parseInt(index);
    }
}