package br.ufal.ic.p2.wepayu.middleware.serviceDatabase;

import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;
import br.ufal.ic.p2.wepayu.controller.employee.EmployeeController;
import br.ufal.ic.p2.wepayu.controller.humanResources.*;
import br.ufal.ic.p2.wepayu.models.DTO.*;
import br.ufal.ic.p2.wepayu.models.Employee.Commissioned.*;
import br.ufal.ic.p2.wepayu.models.Employee.Hourly.*;
import br.ufal.ic.p2.wepayu.models.Employee.Salaried.EmpregadoAssalariado;
import br.ufal.ic.p2.wepayu.models.Payment.*;
import br.ufal.ic.p2.wepayu.models.Unionized.*;

public class DataRetriever {

    protected void setEmployee(EmployeeDTO empDTO) throws ExceptionCriarEmpregado {

        if (empDTO.getType().equals("horista")) {
            // recria o funcionario horista

            EmpregadoHorista empHourly = new EmpregadoHorista(empDTO.getName(), empDTO.getAddress(), empDTO.getType(),
                    empDTO.getSalary());
            empHourly.setSindicalizado(Boolean.parseBoolean(empDTO.getUnionized()));

            // informa se o empregado é sindicalizado ou não.

            if (empDTO.getList() != null) {

                for (String cardPoint : empDTO.getList()) {

                    String[] date = cardPoint.split(";");

                    CartaoPontos card = new CartaoPontos(date[0], Float.parseFloat(date[1]));

                    empHourly.addCartaoPontos(card);
                }
            }
            empHourly.setPaymentDay(empDTO.getPaymentDay());
            EmployeeController.Empregados.put(empDTO.getIndex(), empHourly);

        } else if (empDTO.getType().equals("comissionado")) {

            EmpregadoComissionado empCommissioned = new EmpregadoComissionado(empDTO.getName(), empDTO.getAddress(),
                    empDTO.getType(), empDTO.getSalary(), empDTO.getCommission());
            empCommissioned.setSindicalizado(Boolean.parseBoolean(empDTO.getUnionized()));

            if (empDTO.getList() != null) {
                for (String cardPoint : empDTO.getList()) {

                    String[] date = cardPoint.split(";");

                    Sale sale = new Sale(date[0], Float.parseFloat(date[1]));

                    empCommissioned.addSale(sale);
                }
            }
            empCommissioned.setPaymentDay(empDTO.getPaymentDay());
            EmployeeController.Empregados.put(empDTO.getIndex(), empCommissioned);

        } else if (empDTO.getType().equals("assalariado")) {
            EmpregadoAssalariado empSalaried = new EmpregadoAssalariado(empDTO.getName(), empDTO.getAddress(),
                    empDTO.getType(), empDTO.getSalary());

            empSalaried.setSindicalizado(Boolean.parseBoolean(empDTO.getUnionized()));
            empSalaried.setPaymentDay(empDTO.getPaymentDay());
            EmployeeController.Empregados.put(empDTO.getIndex(), empSalaried);
        }

        // verifica se o index do empregado é maior para poder se associar
        if (EmployeeController.index <= Integer.parseInt(empDTO.getIndex())) {
            EmployeeController.index = Integer.parseInt(empDTO.getIndex());
        }

    }

    protected void setUnionized(UnionizedDTO unionDTO) {
        // cria um objeto do tipo sindicato
        Unionized union = new Unionized(unionDTO.getIndex(), unionDTO.getEmployeeID(), unionDTO.getUnionFee());
        if (unionDTO.getListService() != null) {
            for (String service : unionDTO.getListService()) {
                // adiciona no empregado
                String[] data = service.split(";");
                ServiceFee servicefee = new ServiceFee(data[0], Float.parseFloat(data[1]));

                union.addServiceFee(servicefee);
            }

        }
        if (EmployeeController.Empregados.get(unionDTO.getEmployeeID()).getSindicalizado()) {
            UnionServiceController.addUnionized(unionDTO.getIndex(), union);
            EmployeeController.Empregados.get(unionDTO.getEmployeeID()).setUnionized(union);
        }

    }

    protected void setPayment(PaymentDTO payDTO) {

        if (payDTO.getName().equals("emMaos")) {
            Payment payment = new PaymentInHands(payDTO.getEmployeeID(), payDTO.getName());
            // vincula o pagamento ao empregado
            EmployeeController.Empregados.get(payDTO.getEmployeeID()).setMethodPayment(payment);
            // salva no hashmap
            PaymentController.methodsPayment.put(payDTO.getEmployeeID(), payment);
        } else if (payDTO.getName().equals("correios")) {
            Payment payment = new PaymentInMail(payDTO.getEmployeeID(), payDTO.getName());
            // vincula o pagamento ao empregado
            EmployeeController.Empregados.get(payDTO.getEmployeeID()).setMethodPayment(payment);
            // salva no hashmap
            PaymentController.methodsPayment.put(payDTO.getEmployeeID(), payment);
        } else {
            // cria metodo do tipo pagamento em banco
            Payment payment = new PaymentInBank(payDTO.getEmployeeID(), payDTO.getName(), payDTO.getBank(),
                    payDTO.getAgency(), payDTO.getAccountNumber());
            // vincula ao cliente
            EmployeeController.Empregados.get(payDTO.getEmployeeID()).setMethodPayment(payment);
            // salva no hashmap
            PaymentController.methodsPayment.put(payDTO.getEmployeeID(), payment);
        }
    }

    protected void setPaymentDay(String entitie) {
        PayrollController.PaymentDays.add(entitie);
    }
}
