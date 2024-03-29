package br.ufal.ic.p2.wepayu.middleware;

import java.util.ArrayList;
import java.util.HashMap;

import br.ufal.ic.p2.wepayu.controller.employee.EmployeeController;
import br.ufal.ic.p2.wepayu.controller.humanResources.PaymentController;
import br.ufal.ic.p2.wepayu.controller.humanResources.PayrollController;
import br.ufal.ic.p2.wepayu.controller.humanResources.UnionServiceController;
import br.ufal.ic.p2.wepayu.models.StrategyDB.CreateEntityDB;
import br.ufal.ic.p2.wepayu.models.StrategyDB.DBConnection;
import br.ufal.ic.p2.wepayu.models.StrategyDB.DeleteEntityDB;
import br.ufal.ic.p2.wepayu.utils.EnumType.getEnumDatabase;

public class DBHandler {

    private static String file;

    public void getData(getEnumDatabase type, DBConnection db) throws Exception {

        searchDatabase(type);
        db.connect(file);

        if (type == getEnumDatabase.Employee) {
            EmployeeController.index += 1;
        }
    }

    public <T> void uploadData(getEnumDatabase type, CreateEntityDB db, HashMap<String, T> map)
            throws Exception {

        selectDatabase(type);
        db.connect(file, map);

    }

    public void uploadData(getEnumDatabase type, CreateEntityDB db, ArrayList<String> list) throws Exception {

        selectDatabase(type);
        db.connect(file, list);

    }

    public void removeData(getEnumDatabase type, DeleteEntityDB db, String key)
            throws Exception {

        selectDatabase(type);

        db.connect(file, key);
        selectDatabase(getEnumDatabase.Payment);
        db.connect(file, key);
    }

    public void resetData(getEnumDatabase type, DBConnection db) throws Exception {

        searchDatabase(type);
        db.connect(file);

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