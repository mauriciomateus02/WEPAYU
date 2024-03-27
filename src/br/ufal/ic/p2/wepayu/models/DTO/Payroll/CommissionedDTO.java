package br.ufal.ic.p2.wepayu.models.DTO.Payroll;

import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;

public class CommissionedDTO {

    private static double salarieFixed = 0.0;
    private static double sales = 0.0;
    private static double commission = 0.0;
    private static double salariedCommissioned = 0.0;
    private static double discount = 0.0;
    private static double netpayCommissioned = 0.0;

    public static double getSalarieFixed() {
        return salarieFixed;
    }

    public static double getSales() {
        return sales;
    }

    public static double getCommission() {
        return commission;
    }

    public static double getSalariedCommissioned() {
        return salariedCommissioned;
    }

    public static double getDiscount() {
        return discount;
    }

    public static double getNetpayCommissioned() {
        return netpayCommissioned;
    }

    public static void resetData() {
        salarieFixed = 0.0;
        sales = 0.0;
        commission = 0.0;
        salariedCommissioned = 0.0;
        discount = 0.0;
        netpayCommissioned = 0.0;
    }

    public static void addCommissionedDTO(String salarieFixed, String sales, String commission,
            String salariedCommissioned,
            String discount, String netpay) {
        salarieFixed = salarieFixed.replace(" ", "");
        sales = sales.replace(" ", "");
        commission = commission.replace(" ", "");
        salariedCommissioned = salariedCommissioned.replace(" ", "");
        discount = discount.replace(" ", "");
        netpay = netpay.replace(" ", "");

        CommissionedDTO.salarieFixed += Double.parseDouble(Conversor.converterInvertedCharacter(salarieFixed));
        CommissionedDTO.sales += Double.parseDouble(Conversor.converterInvertedCharacter(sales));
        CommissionedDTO.commission += Double.parseDouble(Conversor.converterInvertedCharacter(commission));
        CommissionedDTO.salariedCommissioned += Double
                .parseDouble(Conversor.converterInvertedCharacter(salariedCommissioned));
        CommissionedDTO.discount += Double.parseDouble(Conversor.converterInvertedCharacter(discount));
        CommissionedDTO.netpayCommissioned += Double.parseDouble(Conversor.converterInvertedCharacter(netpay));
    }
}
