package br.ufal.ic.p2.wepayu.models.DTO.Payroll;

import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;

public class SalariedDTO {

    private static double salaried = 0.0;
    private static double discountSalaried = 0.0;
    private static double netpaySalaried = 0.0;

    public static double getSalaried() {
        return salaried;
    }

    public static double getDiscountSalaried() {
        return discountSalaried;
    }

    public static double getNetpaySalaried() {
        return netpaySalaried;
    }

    public static void resetData() {
        salaried = 0.0;
        discountSalaried = 0.0;
        netpaySalaried = 0.0;
    }

    public static void addSalariedDTO(String salaried, String discount, String netpay) {

        salaried = salaried.replace(" ", "");
        discount = discount.replace(" ", "");
        netpay = netpay.replace(" ", "");

        SalariedDTO.salaried += Double.parseDouble(Conversor.converterInvertedCharacter(salaried));
        SalariedDTO.discountSalaried += Double.parseDouble(Conversor.converterInvertedCharacter(discount));
        SalariedDTO.netpaySalaried += Double.parseDouble(Conversor.converterInvertedCharacter(netpay));
    }
}
