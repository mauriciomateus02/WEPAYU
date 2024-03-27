package br.ufal.ic.p2.wepayu.models.DTO.Payroll;

import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;

public class HourlyDTO {

    private static int hourly = 0;
    private static int hourlyExtra = 0;
    private static double salarieHourly = 0.0;
    private static double discountHourly = 0.0;
    private static double netpayHourly = 0.0;

    public static int getHourly() {
        return hourly;
    }

    public static int getHourlyExtra() {
        return hourlyExtra;
    }

    public static double getSalarieHourly() {
        return salarieHourly;
    }

    public static double getDiscountHourly() {
        return discountHourly;
    }

    public static double getNetpayHourly() {
        return netpayHourly;
    }

    public static void resetData() {

        HourlyDTO.hourly = 0;
        HourlyDTO.hourlyExtra = 0;
        HourlyDTO.salarieHourly = 0.0;
        HourlyDTO.discountHourly = 0.0;
        HourlyDTO.netpayHourly = 0.0;
    }

    public static void addHourlyDTO(String hourly, String extra, String salaried, String discount, String netpay) {
        hourly = hourly.replace(" ", "");
        extra = extra.replace(" ", "");
        salaried = salaried.replace(" ", "");
        discount = discount.replace(" ", "");
        netpay = netpay.replace(" ", "");

        HourlyDTO.hourly += Integer.parseInt(hourly);
        HourlyDTO.hourlyExtra += Integer.parseInt(extra);
        HourlyDTO.salarieHourly += Double.parseDouble(Conversor.converterInvertedCharacter(salaried));
        HourlyDTO.discountHourly += Double.parseDouble(Conversor.converterInvertedCharacter(discount));
        HourlyDTO.netpayHourly += Double.parseDouble(Conversor.converterInvertedCharacter(netpay));
    }
}
