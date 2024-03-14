package br.ufal.ic.p2.wepayu.utils.Validator;

import br.ufal.ic.p2.wepayu.Exception.SaleAmountException;
import br.ufal.ic.p2.wepayu.utils.Conversor.Conversor;

public class ValidatorSale {

    public static void validateSaleAmount(String amount) throws SaleAmountException {

        try {

            if (amount.isEmpty()) {
                new SaleAmountException("Valor não deve ser nulo.");
            }

            if (amount.contains(",")) {
                // converte o valor para ser possivel converter para float
                // ex.: se o amount é 20,00 ele converte para 20.00
                amount = Conversor.converterInvertedCharacter(amount);

                // converte a quantia para ser analisada
                float money = Float.parseFloat(amount);

                if (money <= 0) {
                    throw new SaleAmountException("Valor deve ser positivo.");
                }
            } else {

                // converte o valor para ser possivel converter para float
                // ex.: se o amount é 20,00 ele converte para 20.00
                amount = Conversor.converterCharacter(amount);

                // converte a quantia para ser analisada
                float money = Float.parseFloat(amount);

                if (money <= 0) {
                    throw new SaleAmountException("Valor deve ser positivo.");
                }
            }

        } catch (Exception e) {
            throw e;
        }
    }

}
