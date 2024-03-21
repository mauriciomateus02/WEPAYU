package br.ufal.ic.p2.wepayu.middleware.serviceDatabese;

import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;
import br.ufal.ic.p2.wepayu.controller.employee.ControllerEmpregado;
import br.ufal.ic.p2.wepayu.controller.humanResources.PaymentController;
import br.ufal.ic.p2.wepayu.controller.humanResources.ControllerFolhaPagamento;
import br.ufal.ic.p2.wepayu.controller.humanResources.UnionServiceController;
import br.ufal.ic.p2.wepayu.models.Employee.Commissioned.EmpregadoComissionado;
import br.ufal.ic.p2.wepayu.models.Employee.Commissioned.Sale;
import br.ufal.ic.p2.wepayu.models.Employee.Hourly.CartaoPontos;
import br.ufal.ic.p2.wepayu.models.Employee.Hourly.EmpregadoHorista;
import br.ufal.ic.p2.wepayu.models.Employee.Salaried.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.models.Payment.Payment;
import br.ufal.ic.p2.wepayu.models.Payment.PagamentoEmBanco;
import br.ufal.ic.p2.wepayu.models.Payment.PaymentInHands;
import br.ufal.ic.p2.wepayu.models.Payment.PaymentInMail;
import br.ufal.ic.p2.wepayu.models.Unionized.ServiceFee;
import br.ufal.ic.p2.wepayu.models.Unionized.Unionized;

public class DataRetriever {

    protected static void setEmployee(String index, String name, String address,
            String type, String unionized,
            float salary, String paymentDay, String[] list) throws ExceptionCriarEmpregado {

        if (type.equals("horista")) {
            // recria o funcionario horista
            EmpregadoHorista emp = new EmpregadoHorista(name, address, type, salary);
            // informa se o empregado é sindicalizado ou não.
            emp.setSindicalizado(Boolean.parseBoolean(unionized));
            emp.setPaymentDay(paymentDay);

            for (String cardPoint : list) {

                String[] date = cardPoint.split(";");

                CartaoPontos card = new CartaoPontos(date[0], Float.parseFloat(date[1]));

                emp.addCartaoPontos(card);
            }
            ControllerEmpregado.Empregados.put(index, emp);

        }

        // verifica se o index do empregado é maior para poder se associar
        if (ControllerEmpregado.index <= Integer.parseInt(index)) {
            ControllerEmpregado.index = Integer.parseInt(index);
        }

    }

    protected static void setEmployee(String index, String name, String address,
            String type, String unionized,
            float salary, String paymentDay) throws ExceptionCriarEmpregado {

        if (type.equals("horista")) {
            // recria o funcionario horista
            EmpregadoHorista emp = new EmpregadoHorista(name, address, type, salary);

            // informa se o empregado é sindicalizado ou não.
            emp.setSindicalizado(Boolean.parseBoolean(unionized));
            // indica o dia de pagamento
            emp.setPaymentDay(paymentDay);

            ControllerEmpregado.Empregados.put(index, emp);

        } else if (type.equals("assalariado")) {
            EmpregadoAssalariado emp = new EmpregadoAssalariado(name, address, type, salary);

            emp.setSindicalizado(Boolean.parseBoolean(unionized));
            emp.setPaymentDay(paymentDay);

            ControllerEmpregado.Empregados.put(index, emp);
        }

        // verifica se o index do empregado é maior para poder se associar
        if (ControllerEmpregado.index <= Integer.parseInt(index)) {
            ControllerEmpregado.index = Integer.parseInt(index);

        }
    }

    protected static void setEmployee(String index, String name, String address,
            String type, String unionized, float salary, float commission, String paymentDay, String[] list)
            throws ExceptionCriarEmpregado {

        if (type.equals("comissionado")) {
            // recria o funcionario horista
            EmpregadoComissionado emp = new EmpregadoComissionado(name, address, type, salary, commission);

            // informa se o empregado é sindicalizado ou não.
            emp.setSindicalizado(Boolean.parseBoolean(unionized));
            // indica o dia de pagamento
            emp.setPaymentDay(paymentDay);

            for (String cardPoint : list) {

                String[] date = cardPoint.split(";");

                Sale sale = new Sale(date[0], Float.parseFloat(date[1]));

                emp.addSale(sale);
            }

            ControllerEmpregado.Empregados.put(index, emp);

        }

        // verifica se o index do empregado é maior para poder se associar
        if (ControllerEmpregado.index <= Integer.parseInt(index)) {
            ControllerEmpregado.index = Integer.parseInt(index);

        }
    }

    protected static void setEmployee(String index, String name, String address,
            String type, String unionized,
            float salary, float commission, String paymentDay) throws ExceptionCriarEmpregado {

        if (type.equals("comissionado")) {
            // recria o funcionario horista
            EmpregadoComissionado emp = new EmpregadoComissionado(name, address, type, salary, commission);
            // informa se o empregado é sindicalizado ou não.
            emp.setSindicalizado(Boolean.parseBoolean(unionized));
            // indica o dia de pagamento
            emp.setPaymentDay(paymentDay);

            ControllerEmpregado.Empregados.put(index, emp);
        }
        // verifica se o index do empregado é maior para poder se associar
        if (ControllerEmpregado.index <= Integer.parseInt(index)) {
            ControllerEmpregado.index = Integer.parseInt(index);
        }
    }

    protected static void setUnionized(String index, String employeeID, String taxaSindicato, String[] listService) {
        // cria um objeto do tipo sindicato
        Unionized union = new Unionized(index, employeeID, Float.parseFloat(taxaSindicato));

        for (String service : listService) {
            // adiciona no empregado
            String[] data = service.split(";");
            ServiceFee servicefee = new ServiceFee(data[0], Float.parseFloat(data[1]));

            union.addServiceFee(servicefee);

        }

        UnionServiceController.addUnionized(index, union);

    }

    protected static void setUnionized(String index, String employeeID, String taxaSindicato) {

        Unionized union = new Unionized(index, employeeID, Float.parseFloat(taxaSindicato));

        UnionServiceController.addUnionized(index, union);

    }

    protected static void setPayment(String employeeID, String method) {
        if (method.equals("emMaos")) {
            Payment payment = new PaymentInHands(employeeID, method);
            // vincula o pagamento ao empregado
            ControllerEmpregado.Empregados.get(employeeID).setMethodPayment(payment);
            // salva no hashmap
            PaymentController.methodsPayment.put(employeeID, payment);
        } else {
            Payment payment = new PaymentInMail(employeeID, method);
            // vincula o pagamento ao empregado
            ControllerEmpregado.Empregados.get(employeeID).setMethodPayment(payment);
            // salva no hashmap
            PaymentController.methodsPayment.put(employeeID, payment);
        }
    }

    protected static void setPayment(String employeeID, String name, String banco, String agencia, String numeroConta) {
        // cria metodo do tipo pagamento em banco
        Payment payment = new PagamentoEmBanco(employeeID, name, banco, agencia, numeroConta);
        // vincula ao cliente
        ControllerEmpregado.Empregados.get(employeeID).setMethodPayment(payment);
        // salva no hashmap
        PaymentController.methodsPayment.put(employeeID, payment);
    }

    public static void setPaymentDay(String entitie) {
        ControllerFolhaPagamento.PaymentDays.add(entitie);
    }
}
