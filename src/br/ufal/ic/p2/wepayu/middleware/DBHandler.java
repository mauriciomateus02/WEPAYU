package br.ufal.ic.p2.wepayu.middleware;

import java.io.FileNotFoundException;
import java.util.HashMap;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;
import br.ufal.ic.p2.wepayu.controller.employee.EmployeeController;
import br.ufal.ic.p2.wepayu.controller.humanResources.PaymentController;
import br.ufal.ic.p2.wepayu.controller.humanResources.UnionServiceController;
import br.ufal.ic.p2.wepayu.middleware.serviceDatabese.PushFiles;
import br.ufal.ic.p2.wepayu.middleware.serviceDatabese.getFiles;
import br.ufal.ic.p2.wepayu.middleware.serviceDatabese.removeLine;
import br.ufal.ic.p2.wepayu.middleware.serviceDatabese.resetFiles;
import br.ufal.ic.p2.wepayu.utils.EnumType.getEnumDatabase;

public class DBHandler {

    private static String file;

    public static void getData(getEnumDatabase type)
            throws FileNotFoundException {

        switch (type) {
            case Employee:
                EmployeeController.Empregados = new HashMap<>();
                file = "database/Employee.txt";
                break;
            case Unionized:
                UnionServiceController.employeesUnionzed = new HashMap<>();
                file = "database/Unionized.txt";
                break;
            case Payment:
                PaymentController.methodsPayment = new HashMap<>();
                file = "database/Payment.txt";
                break;
            default:
                file = null;
                break;
        }
        getFiles.getEntites(file);

        if (type == getEnumDatabase.Employee) {
            EmployeeController.index += 1;
        }
    }

    public static <T> void uploadData(getEnumDatabase type, HashMap<String, T> map)
            throws EmpregadoNaoExisteException, ExceptionCriarEmpregado {

        switch (type) {
            case Employee:
                file = "database/Employee.txt";
                break;
            case Unionized:
                file = "database/Unionized.txt";
                break;
            case Payment:
                file = "database/Payment.txt";
                break;
            case Default:
                file = "ok/teste.txt";
            default:
                break;
        }

        PushFiles.uploadData(file, map);
    }

    public static void removeData(getEnumDatabase type, String key)
            throws FileNotFoundException, EmpregadoNaoExisteException {

        switch (type) {
            case Employee:
                file = "database/Employee.txt";
                break;
            case Unionized:
                file = "database/Unionized.txt";
                break;
            case Payment:
                file = "database/Payment.txt";
                break;
            case Default:
                file = "ok/teste.txt";
            default:
                break;
        }

        removeLine.removeEntities(file, key);
    }

    public static void resetData(getEnumDatabase type) throws FileNotFoundException {
        switch (type) {
            case Employee:
                EmployeeController.Empregados = new HashMap<>();

                file = "database/Employee.txt";
                break;
            case Unionized:
                UnionServiceController.employeesUnionzed = new HashMap<>();
                file = "database/Unionized.txt";
                break;
            case Payment:
                PaymentController.methodsPayment = new HashMap<>();
                file = "database/Payment.txt";
                break;
            default:
                file = null;
                break;
        }

        resetFiles.resetData(file);
    }

}
