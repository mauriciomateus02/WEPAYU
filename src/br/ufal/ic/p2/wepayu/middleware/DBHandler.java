package br.ufal.ic.p2.wepayu.middleware;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

import br.ufal.ic.p2.wepayu.Exception.EmpregadoNaoExisteException;
import br.ufal.ic.p2.wepayu.Exception.ExceptionCreatePaymentDay;
import br.ufal.ic.p2.wepayu.Exception.ExceptionCriarEmpregado;
import br.ufal.ic.p2.wepayu.controller.employee.EmployeeController;
import br.ufal.ic.p2.wepayu.controller.humanResources.PaymentController;
import br.ufal.ic.p2.wepayu.controller.humanResources.PayrollController;
import br.ufal.ic.p2.wepayu.controller.humanResources.UnionServiceController;
import br.ufal.ic.p2.wepayu.middleware.serviceDatabase.GetFiles;
import br.ufal.ic.p2.wepayu.middleware.serviceDatabase.PushFiles;
import br.ufal.ic.p2.wepayu.middleware.serviceDatabase.removeLine;
import br.ufal.ic.p2.wepayu.middleware.serviceDatabase.resetFiles;
import br.ufal.ic.p2.wepayu.utils.EnumType.getEnumDatabase;

public class DBHandler {

    private static String file;

    public static void getData(getEnumDatabase type)
            throws FileNotFoundException {

        GetFiles getFiles = new GetFiles();
        searchDatabase(type);

        getFiles.getEntites(file);

        if (type == getEnumDatabase.Employee) {
            EmployeeController.index += 1;
        }
    }

    public static <T> void uploadData(getEnumDatabase type, HashMap<String, T> map)
            throws EmpregadoNaoExisteException, ExceptionCriarEmpregado {

        selectDatabase(type);
        PushFiles.uploadData(file, map);

    }

    public static void uploadData(getEnumDatabase type, ArrayList<String> list) throws ExceptionCreatePaymentDay {

        selectDatabase(type);
        PushFiles.upload(list, file);

    }

    public static void removeData(getEnumDatabase type, String key)
            throws FileNotFoundException, EmpregadoNaoExisteException {

        selectDatabase(type);
        removeLine.removeEntities(file, key);
        selectDatabase(getEnumDatabase.Payment);
        removeLine.removeEntities(file, key);
    }

    public static void resetData(getEnumDatabase type) throws FileNotFoundException {

        searchDatabase(type);
        resetFiles.resetData(file);
    }

    private static void searchDatabase(getEnumDatabase type) {

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
            case PaymentDay:
                PayrollController.PaymentDays = new ArrayList<>();
                file = "database/PaymentDay.txt";
                break;
            default:
                file = null;
                break;
        }
    }

    private static void selectDatabase(getEnumDatabase type) {

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
            case PaymentDay:
                file = "database/PaymentDay.txt";
                break;
            default:
                break;
        }
    }

}